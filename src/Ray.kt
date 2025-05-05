import kotlin.math.max
import kotlin.math.pow

class Ray(val origin: Vector3D, val direction: Vector3D) {

    fun rayColor(
        world: Scene,
        lights: List<DirectionalLight>,
        depth: Int
    ): Vector3D {
        if (depth <= 0) return Vector3D(0.0, 0.0, 0.0)
        val hitRecord = world.hit(this)
        if (hitRecord != null) {
            val mat       = hitRecord.material
            val pt        = hitRecord.point
            val normal    = hitRecord.surfaceNormal
            val viewDir   = (-direction).unit()
            val bias      = normal * 1e-4

            var isLit = false
            for (light in lights) {
                val L = light.direction.unit()
                val shadowRay = Ray(pt + bias, L)
                if (world.hit(shadowRay, 0.001, Double.POSITIVE_INFINITY) == null) {
                    isLit = true
                    break
                }
            }

            var diffuse = Vector3D(0.0, 0.0, 0.0)
            if (isLit && mat is Lambertian) {
                for (light in lights) {
                    val L = light.direction.unit()
                    val shadowRay = Ray(pt + bias, L)
                    if (world.hit(shadowRay, 0.001, Double.POSITIVE_INFINITY) == null) {
                        val lambertFactor = max(normal.dot(L), 0.0)
                        diffuse += mat.albedo * light.intensity * lambertFactor
                    }
                }
            }

            var specular = Vector3D(0.0, 0.0, 0.0)
            if (isLit && mat is Metal) {
                for (light in lights) {
                    val L = light.direction.unit()
                    val shadowRay = Ray(pt + bias, L)
                    if (world.hit(shadowRay, 0.001, Double.POSITIVE_INFINITY) == null) {
                        val reflectDir = reflect(-L, normal).unit()
                        val specAngle  = max(viewDir.dot(reflectDir), 0.0)
                        val specFactor = specAngle.pow(50.0)
                        specular += mat.albedo * light.intensity * specFactor
                    }
                }
            }

            val scatterResult = mat.scatter(this, hitRecord)
            val indirect = scatterResult?.let { (scatteredRay, attenuation) ->
                attenuation * scatteredRay.rayColor(world, lights, depth - 1)
            } ?: Vector3D(0.0, 0.0, 0.0)

            return diffuse + specular + indirect
        }

        // background gradient
        val unitDir = direction.unit()
        val t = 0.5 * (unitDir.y + 1.0)
        return Vector3D(1.0, 1.0, 1.0) * (1.0 - t) +
                Vector3D(0.5, 0.7, 1.0) * t
    }

    /** Reflect vector v around normal n */
    private fun reflect(v: Vector3D, n: Vector3D): Vector3D =
        v - n * (2.0 * v.dot(n))
}