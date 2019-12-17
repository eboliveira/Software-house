# Software-house
A project developed to Concorrent Programming discipline.

## Description
This project simulate the behaviour of a software house. He represents Devolopers and Project Managers with semaphores and he has the capacity of start a project, add new features to project, execute features and finish the project.

## Flow
1. When a request to start project arrives, then is acquired a Project Manager to talk about the project.
2. When the talk is finished, so the number of devs is defined and it's acquired.
3. When all the devs are acquired, then the features beggins to execute.
4. When is executing the features, has the probability of 20% to a new feature is added on the execution of project.
5. When all features is finished, then the developers and project manager is available again.
