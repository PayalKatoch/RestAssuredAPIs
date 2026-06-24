pipeline {

    agent any

    options {
        skipDefaultCheckout(true)
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
                sh 'docker run --rm api-automation'
            }
        }
    }
}