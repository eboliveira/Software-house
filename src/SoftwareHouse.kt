import java.util.concurrent.Semaphore
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.random.Random

// A software house that haves 5 developers and 2 project managers. Has the capacity for execute 2 simultaneous projects.
class SoftwareHouse {
    // Developer are represented by a semaphore
    private val developer = Semaphore(5)
    // Project Manager are represented by a semaphore
    private val projectManager = Semaphore(2)
    // Lock to signal when a project has finished
    private val projectFinished = ReentrantLock()
    private val projectFinishedCond = projectFinished.newCondition()

    fun startProject(name: String) {
        // try to get a project manager to project
        projectManager.acquire()
        println("Project Manager talking about project $name")
        // time representing the talk with the client about the project
        Thread.sleep(Random.nextLong(10000))
        // Number of devs allocated to project
        val devNumber = Random.nextInt(1, 4)
        println("Trying to get $devNumber developers to execute project $name")
        // Try to acquire developers
        developer.acquire(devNumber)
        // Number of features the project will have
        val nFeatures = AtomicInteger(Random.nextInt(1, 10))
        // Project instantiation
        val project = Project(name, nFeatures, devNumber, projectFinished, projectFinishedCond)
        println("Project $name started with $devNumber developers and has ${nFeatures.get()} features")
        // Thread that executes the project
        Thread { project.execute() }.start()
        projectFinished.withLock {
            // While project is not finished, wait
            while(project.nFeatures.get() > 0){
                projectFinishedCond.await()
            }
        }
        // When project finish, call the function to finish project
        finishProject(project)
    }

    private fun finishProject(project: Project){
        // Free the developers and project manager
        developer.release(project.devNumber)
        projectManager.release()
        println("Project ${project.name} finished")
    }
}