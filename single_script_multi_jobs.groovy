listView('My New View') {
    description('This is a new view')
    filterBuildQueue()
    filterExecutors()
    jobs {
        name('JobA')
        name('JobB')
        name('JobC')
    }
}

job('JobA') {
    steps {
        shell('echo "Running Job A"')
        shell('echo "Step 2"')
    }
    publishers {
        downstream('JobB')
    }
}

job('JobB') {
    steps {
        shell('echo "Running Job B"')
        shell('echo "Step 1"')
        shell('echo "Step 2"')
    }
    publishers {
        downstream('JobC')
    }
}

job('JobC') {
    steps {
        shell('echo "Running Job C"')
        shell('echo "Step 1"')
        shell('echo "Step 2"')
        shell('echo "Step 3"')
    }
}

listView('My New View') {
    columns {
        status()
        weather()
        name()
        lastSuccess()
        lastFailure()
        lastDuration()
    }
}
