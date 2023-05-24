job('job-4') {
    description('This is an example DSL job-4')

    steps {
        shell('echo "This is a fourth job"')
    }

    publishers {
        downstream(('job-5')
        }
    }

