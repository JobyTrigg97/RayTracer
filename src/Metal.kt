class Metal(val albedo: Vector3D, val fuzz: Double) : Material {
    override fun scatter(rayIn: Ray, hitRecord: HitRecord): Pair<Ray, Vector3D>? {
        val reflected = reflect(rayIn.direction.unit(), hitRecord.surfaceNormal)
        val scatterDirection = reflected + RandomPointInSphere() * fuzz
        val scattered = Ray(hitRecord.point, scatterDirection)
        return if (scattered.direction.dot(hitRecord.surfaceNormal) > 0) Pair(scattered, albedo) else null
    }

    /**
     * Reflect vector v about normal n.
     */
    fun reflect(v: Vector3D, n: Vector3D): Vector3D = v - n * 2.0 * v.dot(n)
}