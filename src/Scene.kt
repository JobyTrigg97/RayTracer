class Scene(private val objects: List<Hittable>) {
    fun hit(ray: Ray, tMin: Double = 0.001, tMax: Double = Double.MAX_VALUE) : HitRecord?{
        var closestSoFar = tMax
        var hitRecord: HitRecord? = null

        for (obj in objects){
            val rec = obj.hit(ray, tMin, closestSoFar)
            if (rec != null) {
                closestSoFar = rec.t
                hitRecord = rec
            }
        }
        return hitRecord
    }
}