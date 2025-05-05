class Metal(
    override val albedo: Vector3D,
    private val fuzz: Double
) : Material {

    override fun scatter(rayIn: Ray, hitRecord: HitRecord): Pair<Ray, Vector3D>? {
        val unitIncoming = rayIn.direction.unit()
        val reflected = reflect(unitIncoming, hitRecord.surfaceNormal)
        val scatterDirection = reflected + RandomPointInSphere() * fuzz

        return if (scatterDirection.dot(hitRecord.surfaceNormal) > 0.0) {
            Pair(Ray(hitRecord.point, scatterDirection.unit()), albedo)
        } else {
            null
        }
    }

    private fun reflect(v: Vector3D, n: Vector3D): Vector3D = v - n * (2.0 * v.dot(n))
}
