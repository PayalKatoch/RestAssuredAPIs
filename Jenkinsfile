pipeline {

    agent any

    parameters {
        choice(name: 'ENV', choices: ['qa', 'staging', 'prod'], description: 'Select the test environment')
    }

    options {
        skipDefaultCheckout(true)
        timestamps()
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/PayalKatoch/RestAssuredAPIs.git'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t api-automation .'
            }
        }

        stage('Run API Tests') {
            steps {
                sh """
                    docker run --rm \
                        -v \${WORKSPACE}/allure-results:/app/target/allure-results \
                        -v \${WORKSPACE}/surefire-reports:/app/target/surefire-reports \
                        api-automation mvn clean test -Denv=${params.ENV}
                """
            }
        }
    }

    post {
        always {
            allure includeProperties: false,
                   results: [[path: 'allure-results']]

            junit 'surefire-reports/*.xml'
        }
        failure {
            echo "API Tests failed on ${params.ENV} environment!"
        }
        success {
            echo "API Tests passed on ${params.ENV} environment!"
        }
    }
}
