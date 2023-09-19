import json, os
import shutil
from pylab import *
import time
from collections import OrderedDict
import re
import argparse

def has_error_on_method(m_id, log_file):
    print("--")
    print(log_file)
    pattern = f'(?s)>{m_id}(.*?)<{m_id}'
    with open(log_file, 'r') as file:
        content = file.read()
        matches = re.findall(r""+pattern, content)
    #print(matches)
    return len(list(filter(lambda x: 'xception' 
                           in str(x) or 'error' in str(x).lower(), matches))) > 0
    

    
def process_config_folder(fldr):
    print(fldr)
    files = [os.path.join(fldr, x) for x in os.listdir(fldr) if '_resume' in x and '.validated' not in x]
    valids = 0
    for file in files:
        if os.path.exists(file + ".validated"):
            continue
        test_id = file.split("_")[-1].replace(".json", "")
        matching_logcat_files =  [os.path.join(fldr, x) for x in os.listdir(fldr) if test_id in x and 'logcat' in x]
        if len(matching_logcat_files) == 0:
            os.remove(file)
            continue
        valids +=1
        matching_logcat_file = matching_logcat_files[0]
        #invoked_m_info = extract_values_from_files([file])
        json_content = parse_json(file)
        for method_inv in list(json_content['invoked_methods'].keys()):
            # assume that errors occur on all execx
            if has_error_on_method(method_inv, matching_logcat_file):
                print(f"exec invalida: {method_inv}")
                json_content['invoked_methods'].pop(method_inv)
                
        with open(file + '.validated', 'w') as jfile:
            json.dump(json_content, jfile, indent=1)
    return len(json_content['invoked_methods']) > 0 if valids > 0 else True

def parse_json(filepath):
    js = {}
    with open(filepath, 'r') as j:
        js = json.load(j)
    return js


def extract_values_from_files(files):
    rm_val = "" #"MeasureSymmetricTest_test_"
    function_dict = {}
    jsons = [parse_json(x) for x in files if '.json' in x and 'validated' in x]
    for j_file in jsons:
        #print(json.dumps(j_file['invoked_methods'], indent=1))
        for method, invs in j_file['invoked_methods'].items():
            mname = method.replace(rm_val, "")
            for invok in invs.values():
                #print(invok)
                if 'elapsed_time' in invok:
                    function_dict[mname] = {'times': [invok['elapsed_time']], 'energies': [invok['cpu']]} if mname not in function_dict else {'times': function_dict[mname]['times'] + [invok['elapsed_time']] , 'energies': function_dict[mname]['energies'] + [invok['cpu']]}
    return function_dict

def extract_values_from_files(files):
    function_dict = {}
    jsons = [(parse_json(x), x) for x in files if '.json' in x]
    for (j_file, jfilename) in jsons:
        basedir = os.path.dirname(jfilename)
        rm_val = "" if len(basedir.split("_")) == 1 else basedir.split("_")[1] + "_" + ("" if 'test' not in basedir else "test") 
        #print(json.dumps(j_file['invoked_methods'], indent=1))
        for method, invs in j_file['invoked_methods'].items():
            run_id = "_".join(basedir.split("_")[-4:]) if len(basedir.split("_")) <= 7 else "_".join(basedir.split("_")[-5:]) 
            mname = method.replace(rm_val, "") + run_id
            for invok in invs.values():
                #print(invok)
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
        text(x, y, '%.4f' % y, fontsize=5)  # draw above, centered
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


def plot_res(unsorted_res):
    filtro = ""
    res =  OrderedDict(sorted(unsorted_res.items(), key=lambda t: t[0]))
    keys = [ x for x in list(res.keys()) if filtro in x]
    print(keys)
    times = [ b['times'] for a,b in res.items() if filtro in a]
    consumptions = [ b['energies'] for a,b in res.items() if filtro in a]
    gen_box_plot(keys, times, "elapsed time " + filtro)
    gen_box_plot(keys, consumptions, "energy")

def plot_execs(execs):
    test_types = set(map(lambda x: x.split("_")[0], execs))
    print(f"{len(test_types)} test types")
    for test_type in test_types:
        files = []
        for exec_dir in execs:
            files += [os.path.join(exec_dir, x) for x in os.listdir(exec_dir) if '_resume' in x and test_type in exec_dir]
        fc = extract_values_from_files(files)
        plot_res(fc)
    
def main():
    good_execs = []
    list_of_runs = [ x for x in os.listdir() if 'Measure' in x and os.path.isdir(x) ]
    for run_dir in list_of_runs:
        if process_config_folder(run_dir):
            good_execs.append(run_dir) 
    print(good_execs)
    plot_execs(good_execs)

if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument("-d", "--device", help="device", default="")
    parser.add_argument("-p", "--provider", help="provider", default="")
    parser.add_argument("-a", "--algorithm", help="algorithm", default="")
    parser.add_argument("-p", "--primitive", help="primitive", default="")
    main()