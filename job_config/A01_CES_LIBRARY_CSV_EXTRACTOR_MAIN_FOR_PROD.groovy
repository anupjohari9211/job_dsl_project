listView('content studio cc flow') {
    description('This is a new flow')
    filterBuildQueue()
    filterExecutors()

    jobs {
        job('A01. CES_LIBRARY_CSV_EXTRACTOR_MAIN_FOR_PROD') {
            steps {
                shell('echo "Running Job A"')
                shell('echo "Step 2"')
            }
            publishers {
                downstream('JobB')
            }
        }

        regex('A01. CES_LIBRARY_CSV_EXTRACTOR_MAIN_FOR_PROD')  // Include all jobs in the view
    }

    columns {
        status()
        weather()
        name()
        lastSuccess()
        lastFailure()
        lastDuration()
    }
}
