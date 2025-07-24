pipeline {
    agent {
        docker {
            image 'maven:3.9.6-eclipse-temurin-17'
            args '-v /var/run/docker.sock:/var/run/docker.sock -u root'
        }
    }

    environment {
        COMPOSE_PROJECT_NAME = 'eventdemo-app'
    }

    stages {
        stage('Install Tools') {
            steps {
                sh 'apt-get update && apt-get install -y docker-compose'
            }
        }

        stage('Build JAR') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Deploy Docker Container') {
            steps {
                sh '''
                docker rm -f eventdemo-app || true
                docker-compose down || true
                docker-compose up --build -d
                '''
            }
        }
    }

    post {
        always {
            echo "Deployment done via Jenkins + Docker"
        }
    }
}
