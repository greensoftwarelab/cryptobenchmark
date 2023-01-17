from operator import inv
from re import T
import shutil
from manafa.hunter_emanafa import HunterEManafa
from subprocess import TimeoutExpired, Popen, PIPE
from pylab import *
import time
import os, json
import argparse


#CMD="adb shell am instrument -w -m  -e debug false -e class 'com.example.cryptobenchmark.MeasureDigestTest' com.example.cryptobenchmark.test/android.support.test.runner.AndroidJUnitRunner"
CMD="adb shell am instrument -w -m -e debug false -e class 'com.example.cryptobenchmark.{test_class}' {test_package}/{test_runner}"

def execute_shell_command(cmd, args=[], timeout=None):
    command = cmd + " " + " ".join(args) if len(args) > 0 else cmd
    out = bytes()
    err = bytes()
    #print(command)
    proc = Popen(command, stdout=PIPE, stderr=PIPE, shell=True)
    try:
        out, err = proc.communicate(timeout=timeout)
    except TimeoutExpired as e:
        print("command " + cmd + " timed out")
        out = e.stdout if e.stdout is not None else out
        err = e.stderr if e.stderr is not None else err
        proc.kill()
        proc.returncode = 1
    return proc.returncode, out, err

'''def measure(args_obj):
    em = HunterEManafa()
    for i in range(0, args_obj.n_times):
        em.init()
        em.start()
        res, o , e = execute_shell_command(build_exec_cmd(args_obj))
        print(res)
        print(o)
        print(e)
        if res != 0:
            raise Exception(f"Error while running cmd {CMD}")
        em.stop()
        begin = em.perf_events.events[0].time if len(em.perf_events.events) > 1 else em.bat_events.events[0].time  # first sample from perfetto
        end = em.perf_events.events[-1].time if len(em.perf_events.events) > 1 else em.bat_events.events[-1].time  # first s
        p, c, z = em.get_consumption_in_between(begin, end)
        out_file = em.save_final_report(begin)
        print(f"out file {out_file}")
        print(f"Energy consumed: {p} Joules")
        time.sleep(args_obj.sleep_time)'''

def measure(args_obj):
    #arch -x86_64 python anadroid/main.py -t Custom -cmd "ls -al; sleep 30"'
    cmd_prefix = 'arch -x86_64 pyanadroid -run -t Custom -cmd'
    for i in range(0, args_obj.n_times):
        cmd = f'{cmd_prefix} \"{build_exec_cmd(args_obj)}\"'
        print(f"ai vai o cmd {cmd}")
        res, o , e = execute_shell_command(cmd)
        print(res)
        print(o)
        print(e)
        if res != 0:
            raise Exception(f"Error while running cmd {CMD}")
        time.sleep(args_obj.sleep_time)

def fetch_res_files(results_dir=""):
    return [x for x in os.listdir(results_dir) if 'manafa_res' in x]


def parse_json(filepath):
    js = {}
    with open(filepath, 'r') as j:
        js = json.load(j)
    return js

def extract_values_from_files(files):
    rm_val = " " #"MeasureSymmetricTest_test_"
    function_dict = {}
    jsons = [parse_json(x) for x in files]
    for j_file in jsons:
        #print(json.dumps(j_file['invoked_methods'], indent=1))
        for method, invs in j_file['invoked_methods'].items():
            mname = method.replace(rm_val, "")
            for invok in invs.values():
                print(invok)
                if 'elapsed_time' in invok:
                    function_dict[mname] = {'times': [invok['elapsed_time']], 'energies': [invok['cpu']]} if mname not in function_dict else {'times': function_dict[mname]['times'] + [invok['elapsed_time']] , 'energies': function_dict[mname]['energies'] + [invok['cpu']]}
    return function_dict

def gen_box_plot(key_list, list_of_lists, title="ai"):
    # eg gen_box_plot(['group1', 'group2'], [[1, 2],[3, 4]]):
    fig1, en_box = plt.subplots()
    the_list = list_of_lists

    bp_dict = en_box.boxplot(x=the_list,
                             notch=False,  # notch shape
                             vert=True,  # vertical box aligmnent
                             sym='ko',  # red circle for outliers
                             patch_artist=True,  # fill with color
                             )
    i = 0
    for line in bp_dict['medians']:
        x, y = line.get_xydata()[1]  # top of median line
        xx, yy = line.get_xydata()[0]
        text(x, y, '%.2f' % y, fontsize=5)  # draw above, centered
        # text(xx, en_box.get_ylim()[1] * 0.98, '%.2f' % np.average(list_all_samples[i]), color='darkkhaki')
        i = i + 1

        # set colors
    colors = ['lightblue', 'darkkhaki']
    i = 0
    for bplot in bp_dict['boxes']:
        i = i + 1
        bplot.set_facecolor(colors[i % len(colors)])

    xtickNames = plt.setp(en_box, xticklabels=key_list)
    plt.setp(xtickNames, rotation=90, fontsize=5)
    plt.suptitle(title)
    plt.show()


def plot_res(res):
    filtro = ""
    keys = [ x for x in list(res.keys()) if filtro in x]
    times = [ b['times'] for a,b in res.items() if filtro in a]
    consumptions = [ b['energies'] for a,b in res.items() if filtro in a]
    gen_box_plot(keys, times, "elapsed time " + filtro)
    gen_box_plot(keys, consumptions, "energy")
    #gen_box_plot(['aaa', 'bbb'], [[1,2],[3,5]])

def build_apks(args_obj):
    print("building")
    res, o , e = execute_shell_command(build_build_cmd(args_obj))
    print(res)
    print(o)

def install_apks(build_type="debug"):
    print("installing apks")
    res, o , e = execute_shell_command(f"adb install -g -r app/build/outputs/apk/{build_type.lower()}/app-{build_type.lower()}*")
    print(res)
    print(o)
    print(e)
    res, o , e = execute_shell_command(f"adb install -g -r app/build/outputs/apk/androidTest/{build_type.lower()}/app-{build_type.lower()}*")
    print(res)
    print(o)
    print(e)
    

def uninstall_apks(args_obj):
    print("uninstalling apks")
    execute_shell_command("adb shell pm uninstall com.example.cryptobenchmark")
    execute_shell_command(f"adb shell pm uninstall {args_obj.test_package}")


def gen_run_id(args_obj):
    return f'{args_obj.test_class}_{args_obj.provider}_{args_obj.algorithm}_{args_obj.key_len}_{args_obj.input_size}_{args_obj.algorithm_mode + args_obj.padding}'


def save_res_in_id_folder(run_id, file_list):
    if not os.path.exists(run_id):
        os.mkdir(run_id)
    for f in file_list:
        shutil.copy(f, os.path.join(run_id, os.path.basename(f)))


def main(args_obj):
    if args_obj.build: 
        build_apks(args_obj)
    exit(0)
    if args_obj.install:
        install_apks(args_obj.build_type)
    measure(args_obj)
    files = fetch_res_files(results_dir="")
    fc = extract_values_from_files(files)
    run_id = gen_run_id(args_obj, fc)
    save_res_in_id_folder(run_id)
    print(json.dumps(fc, indent=1))
    if args_obj.plot:
        plot_res(fc)
    if args_obj.uninstall:
        uninstall_apks()


def build_exec_cmd(args_obj):
    return CMD.format(test_class=args_obj.test_class, 
    test_runner=args_obj.test_runner,
    test_package=args_obj.test_package)


def build_build_cmd(args_obj):
    prop_keys = get_keys_of_prop_file()
    prop_fmt_keys = list(filter(lambda x: x.upper() in prop_keys, args_obj.__dict__.keys()))
    res = " ".join([f"-P{k.upper()}={args_obj.__dict__[k]}" for k in prop_fmt_keys])
    cmd = f"./gradlew assemble{args_obj.build_type} assembleAndroidTest {res}"
    print(f"build command: {cmd}")
    return cmd


def fetch_from_gradle_prop_file(key, default_val):
    with open('gradle.properties') as f:
        for line in f:
                # Split the line into a key-value pair
                try:
                    k, v = line.strip().split('=')
                    if k == key and v != '':
                        return v
                except:
                    continue
               
        # If no matching key is found, return the default value
        return default_val


def get_keys_of_prop_file():
    x = []
    with open('gradle.properties') as f:
        for line in f:
                # Split the line into a key-value pair
                try:
                    k = line.strip().split('=')[0]
                    x.append(k)
                except:
                    continue
        return x


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument("-b", "--build", help="build", action='store_true', default=False)
    parser.add_argument("-bt", "--build_type", help="build type", type=str, choices=['Debug', 'Release'], default="Release")
    parser.add_argument("-i", "--install", help="install apks", action='store_true', default=False)
    parser.add_argument("-u", "--uninstall", help="uninstall apks", action='store_true', default=False)
    parser.add_argument("-p", "--plot", help="plot results", action='store_true', default=False)
    parser.add_argument("-c", "--test_class", help="test class", default="DigestTest", choices=["MeasureDigestTest",
    "MeasureHMACTest", "MeasureSymmetricTest", "MeasureSymmetricDecryptTest"])
    parser.add_argument("-r", "--test_runner", help="unit test runner", default="android.support.test.runner.AndroidJUnitRunner", choices=["android.support.test.runner.AndroidJUnitRunner"])
    parser.add_argument("-tp", "--test_package", help="test package",  default="com.example.cryptobenchmark.test")
    parser.add_argument("-nt", "--n_times", help="times to repeat each algorithm execution",  default=1, type=int)
    parser.add_argument("-s", "--sleep_time", help="time to sleep betweeen each execution",  default=3, type=int)
    parser.add_argument("-pv", "--provider", help="crypto provider", default=fetch_from_gradle_prop_file("PROVIDER", "AndroidOpenSSL"), type=str)
    parser.add_argument("-is", "--input_size", help="input size",  default=fetch_from_gradle_prop_file("INPUT_SIZE", 128), type=int)
    parser.add_argument("-kl", "--key_len", help="key length",  default=fetch_from_gradle_prop_file("KEY_LEN", 128), type=int)
    parser.add_argument("-a", "--algorithm", help="algorithm to execute",  default=fetch_from_gradle_prop_file("ALGORITHM", "AES"), type=str)
    parser.add_argument("-m", "--algorithm_mode", help="algorithm mode",  default=fetch_from_gradle_prop_file("MODE", ""), type=str)
    parser.add_argument("-padding", "--padding", help="algorithm padding",  default=fetch_from_gradle_prop_file("PADDING", ""), type=str)
    args = parser.parse_args()
    print(args.__dict__)
    main(args_obj=args)
