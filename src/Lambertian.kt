
class Lambertian(override val albedo: Vector3D) : Material {
    override fun scatter(rayIn: Ray, hitRecord: HitRecord): Pair<Ray, Vector3D>? {
        var scatterDirection = hitRecord.surfaceNormal + RandomPointInSphere()
        if(scatterDirection.nearZero())
        {
            scatterDirection = hitRecord.surfaceNormal
        }
        val scattered =  Ray(hitRecord.point, scatterDirection)
        return Pair(scattered, albedo)
    }
}