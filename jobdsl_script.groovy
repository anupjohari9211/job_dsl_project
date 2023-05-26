def loadScriptFromSCM(String scmPath) {
    def scmScript = readFileFromWorkspace(scmPath)
    return evaluate(scmScript)
}

// Load the job creation configuration
def jobConfig = loadScriptFromSCM('job_config/A01_CES_LIBRARY_CSV_EXTRACTOR_MAIN_FOR_PROD.groovy')

// Load the axes configuration
def axesConfig = loadScriptFromSCM('axes/axes_config.groovy')

// Load the build steps
def buildSteps = loadScriptFromSCM('build_steps/build_steps.groovy')

// Create the multi-configuration job
jobConfig.call()

// Modify the multi-configuration job with axes and build steps
matrixProject('Multi-Job Configuration') {
    axes {
        axesConfig.call()
    }
    steps {
        buildSteps.call()
    }
}
