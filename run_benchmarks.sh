# !/bin/bash


INPUT_SIZES=(256 1024 4086 81920)


function testDigest(){
    # Digest
    PROVIDER=$1
    for is in ${INPUT_SIZES[@]}; do
        echo $is $PROVIDER
        python3 benchmark.py -b -i -u -c MeasureDigestTest -nt 1 -s 5 -is $is -pv $PROVIDER
        #python3 benchmark.py -b -i -u -c MeasureDigestTest -nt 25 -s 5 -is $is -pv AndroidOpenSSL
        #python3 benchmark.py -b -i -u -c MeasureDigestTest -nt 25 -s 5 -is $is -pv AndroidOpenSSL
        #python3 benchmark.py -b -i -u -c MeasureDigestTest -nt 25 -s 5 -is $is -pv AndroidOpenSSL
    done
}


testDigest "AndroidOpenSSL"