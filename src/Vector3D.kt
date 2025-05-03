import kotlin.math.sqrt

data class Vector3D(val x: Double, val y: Double, val z: Double){
    operator fun plus(o: Vector3D) = Vector3D(x + o.x, y + o.y, z + o.z)
    operator fun minus(o: Vector3D) = Vector3D(x - o.x, y - o.y, z - o.z)
    operator fun times(s: Double) = Vector3D(x * s, y * s, z * s)
    operator fun div(s: Double) = Vector3D(x / s, y / s, z / s)
    fun dot(o: Vector3D) = x * o.x + y * o.y + z * o.z
    fun length() = sqrt(dot(this))
    fun unit() = this / length()
}

