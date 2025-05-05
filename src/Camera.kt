class Camera(
    val origin: Vector3D,
    private val lowerLeftCorner: Vector3D,
    private val horizontal: Vector3D,
    private val vertical: Vector3D
) {

    fun getRay(u: Double, v: Double): Ray =
        Ray(origin, lowerLeftCorner + horizontal * u + vertical * v - origin)

    companion object {

        fun basicCamera(viewportWidth: Double, viewportHeight: Double, focalLength: Double): Camera {
            val origin = Vector3D(0.0, 0.0, 0.0)
            val horizontal = Vector3D(viewportWidth, 0.0, 0.0)
            val vertical = Vector3D(0.0, viewportHeight, 0.0)
            val lowerLeft = origin - horizontal / 2.0 - vertical   / 2.0 - Vector3D(0.0, 0.0, focalLength)
            return Camera(origin, lowerLeft, horizontal, vertical)
        }
    }
}
