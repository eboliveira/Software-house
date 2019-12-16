fun main() {
    val softwareHouse = SoftwareHouse()
    Thread { softwareHouse.startProject("Alpha") }.start()
    Thread { softwareHouse.startProject("Beta") }.start()
    Thread { softwareHouse.startProject("Gamma") }.start()
}