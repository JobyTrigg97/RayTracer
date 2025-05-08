import kotlin.math.abs
import kotlin.math.sqrt

data class Vector3D(val x: Double, val y: Double, val z: Double){
    operator fun plus(o: Vector3D) = Vector3D(x + o.x, y + o.y, z + o.z)
    operator fun minus(o: Vector3D) = Vector3D(x - o.x, y - o.y, z - o.z)
    operator fun times(o: Vector3D) = Vector3D(x * o.x, y * o.y, z * o.z)
    operator fun times(s: Double) = Vector3D(x * s, y * s, z * s)
    operator fun div(s: Double) = Vector3D(x / s, y / s, z / s)
    fun dot(o: Vector3D) = x * o.x + y * o.y + z * o.z
    fun length() = sqrt(dot(this))
    fun unit() = this / length()
    fun nearZero() = abs(x) < 1e-8 && abs(y) < 1e-8 && abs(z) < 1e-8
    operator fun unaryMinus() = Vector3D(-x, -y, -z)
    fun cross(o: Vector3D) = Vector3D(
        y * o.z - z * o.y,
        z * o.x - x * o.z,
        x * o.y - y * o.x
    )
}

