pipeline {

    agent any

    parameters {
        choice(name: 'ENV', choices: ['qa', 'staging', 'prod'], description: 'Select the test environment')
    }

    tools {
        allure 'allure'
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
                sh 'docker run --name api-test-run api-automation mvn clean test -Denv=${ENV}'
            }
            post {
                always {
                    sh 'docker cp api-test-run:/app/target/allure-results ./allure-results || true'
                    sh 'docker cp api-test-run:/app/target/surefire-reports ./surefire-reports || true'
                    sh 'docker rm api-test-run || true'
                }
            }
        }
    }

    post {
        always {
            allure includeProperties: false,
                   results: [[path: 'allure-results']]
            junit allowEmptyResults: true, testResults: 'surefire-reports/*.xml'
        }
        failure {
            echo "API Tests failed on ${params.ENV} environment!"
        }
        success {
            echo "API Tests passed on ${params.ENV} environment!"
        }
    }
}
