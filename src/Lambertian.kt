import kotlin.random.Random

class Lambertian(val albedo: Vector3D) : Material {
    override fun scatter(rayIn: Ray, hitRecord: HitRecord): Pair<Ray, Vector3D>? {
        var scatterDirection = hitRecord.surfaceNormal + RandomPointInSphere()
        if(scatterDirection.nearZero())
        {
            scatterDirection = hitRecord.surfaceNormal
        }
        val scattered =  Ray(hitRecord.point, scatterDirection)
        return Pair(scattered, albedo)
    }

    fun RandomPointInSphere(): Vector3D{
        while (true) {
            val p = Vector3D(
                Random.nextDouble(-1.0, 1.0),
                Random.nextDouble(-1.0, 1.0),
                Random.nextDouble(-1.0, 1.0)
            )
            if (p.dot(p) < 1.0) return p.unit()
        }
    }
}