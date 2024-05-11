pipeline{
    agent any

    stages{
        stage('Build'){
            steps{
                echo "Building the code using Gradle Tool, to compile and package the code"
            }
        }

        stage('Unit and Integration Tests'){
            steps{
                echo "Unit tests are executed using TestNG tool to ensure code functions"

                echo "Integration tests are executed using Cypress tool to ensure different components of the application work together"
            }

            post{
                success{
                    emailext(
                        to: "vvarshitha20@gmail.com",
                        subject:"Unit and Integration Test Status",
                        body:"Unit and Integration Tests are Successful!",
                        attachLog: true
                    )
                }

                failure{
                    emailext(
                        to: "vvarshitha20@gmail.com",
                        subject:"Unit and Integration Test Status",
                        body:"Unit and Integration Tests are Failed!",
                        attachLog: true
                    )
                }
            }
        }

        stage('Code Analysis'){
            steps{
                echo "SonarQube tool is used to analyse the code and ensure it meets industry standards"
            }
        }

        stage('Security Scan'){
            steps{
                echo "Security scan on the code is performaed using OWSP ZAP tool to identify vulnerabilities"
            }

            post {
                success {
                    emailext(
                        to: "vvarshitha20@gmail.com",
                        subject:"Status of Security Scan",
                        body:"Security Scan is Successful!",
                        attachLog: true
                    )
                }

                failure {
                    emailext(
                        to: "vvarshitha20@gmail.com",
                        subject:"Security Scan Status",
                        body:"Security Scan Failed!",
                        attachLog: true
                    )
                }
            }
        }

        stage('Deploy to Staging'){
            steps{
                echo "AWS EC2 instance is used to deploy the application to a staging server"
            }
        }

        stage('Integration Tests on Staging'){
            steps{
                echo "Selenium tool is used for integration tests on the staging environment to ensure application functions"
            }
        } 

        stage('Deploy to Production'){
            steps{
                echo "AWS EC2 instance is used to deploy the application to a production server"
            }
        }
    }
}