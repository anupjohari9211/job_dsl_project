steps {
    shell('java -server -classpath ".:/opt/mlp/content-studio/lib/machine-learning-csod-cluster-computation-jar-0.0.1-SNAPSHOT-allinone.jar:/opt/mlp/library-loader/resources/" com.csod.pathshala.library.ces.CesLibraryCsvExtractorMain -c ${clientName} -e ces_prod_latest -k ${key_store_passwd} -n ${environment} -f ${ingestionBucket} -g true"')
}
