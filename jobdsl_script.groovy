def loadScriptFromSCM(String scmPath) {
    def scmScript = readFileFromWorkspace(scmPath)
    return evaluate(scmScript)
}

// Load the job creation configuration
def jobConfig = loadScriptFromSCM('job_config/A01_CES_LIBRARY_CSV_EXTRACTOR_MAIN_FOR_PROD.groovy')

