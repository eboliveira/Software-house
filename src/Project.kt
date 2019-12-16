import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.random.Random

class Project(val name: String, var nFeatures: AtomicInteger, var devNumber: Int, val finishProject: ReentrantLock, val projectFinishedCond: Condition) {
    private fun executeFeature(){
        nFeatures.getAndDecrement()
        println("Feature executed in project $name")
    }

    // executes the project while have features
    fun execute(){
        while(nFeatures.get() > 0){
            Thread.sleep(1000)
            // has 20% chance to appears a new feature
            if(Random.nextInt(10) >= 8 ){
                addFeature()
            }
            executeFeature()
        }
        finishProject.withLock {
            // when project finish, do the signal
            projectFinishedCond.signalAll()
        }
    }

    private fun addFeature(){
        nFeatures.incrementAndGet()
        println("New feature added in project $name")
    }
}