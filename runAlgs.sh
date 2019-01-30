#!/bin/bash
problems="HydroDynamics AucMaximization FacilityPlacement NeuralNetDoublePoleBalancing"
algs="NSGAII PAES IBEA SPEA2 mIBEA GDE3"
rm -f "runMain.txt"
rm -f "erros"

function addToExecution {
    problem=$1
    obj=$2
    alg=$3
    k=0
    l=0
    popSize=100
    filename="saida_"$problem"_"$obj"_"$alg
    filenameer="errsaida_"$problem"_"$obj"_"$alg
    
    rm ./$filename
    rm ./$filenameer
                    
    echo "java -Xms1024m -Xmx1024m -cp target/jMetalHyperHeuristicHelper-1.0-SNAPSHOT.jar:target/lib/* br.usp.poli.pcs.lti.jmetalhhhelper.main.Experiment $problem $obj $k $l $popSize $alg  >> $filename 2>>$filenameer" >> "runMain.txt"
}  

for problem in $problems
do
	for alg in $algs	
    do
        addToExecution $problem 2 $alg
    done
done





cat "runMain.txt" | xargs -I CMD -P 2  bash -c CMD &
wait
