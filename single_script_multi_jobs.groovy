listView('My New View') {
    description('This is a new view')
    filterBuildQueue()
    filterExecutors()
    jobs {
        job('JobA') {
            steps {
                // Build steps for Job A
                shell('echo "Running Job A"')
                shell('echo "Step 2"')
	    }
            publishers {
                downstream('JobB') {
                    // Define downstream relationship between Job A and Job B
                }
            }
        }
        job('JobB') {
            steps {
                // Build steps for Job B
                shell('echo "Running Job B"')
                shell('echo "Step 1"')
                shell('echo "Step 2"')
            }
            publishers {
                downstream('JobC') {
                    // Define downstream relationship between Job B and Job C
                }
            }
        }
        job('JobC') {
            steps {
                // Build steps for Job C
                shell('echo "Running Job C"')
                shell('echo "Step 1"')
                shell('echo "Step 2"')
                shell('echo "Step 3"')
            }
        }
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