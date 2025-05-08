open class HittableGroup(
    private val members: List<Hittable>
) : Hittable {


    override fun hit(ray: Ray, tMin: Double, tMax: Double): HitRecord? {
        var closestSoFar = tMax
        var closestHit: HitRecord? = null

        for (hittable in members) {
            val hitResult = hittable.hit(ray, tMin, closestSoFar)
            if (hitResult != null) {
                closestSoFar = hitResult.t
                closestHit    = hitResult
            }
        }
        return closestHit
    }
}