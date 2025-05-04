import kotlin.let

class Ray(val origin: Vector3D, val direction: Vector3D){

    fun rayColor(world: Scene, depth: Int): Vector3D {
        if (depth <= 0) return Vector3D(0.0, 0.0, 0.0)
        val hitRecord = world.hit(this)
        hitRecord?.let {
            val scatterResult = it.material.scatter(this, it)
            scatterResult?.let { (scattered, attenuation) ->
                return (attenuation * scattered.rayColor(world, depth - 1))
            }
            return Vector3D(0.0, 0.0, 0.0)
        }
        val unitDir = direction.unit()
        val t = 0.5 * (unitDir.y + 1.0)
        return Vector3D(1.0, 1.0, 1.0) * (1.0 - t) + Vector3D(0.5, 0.7, 1.0) * t

    }
}