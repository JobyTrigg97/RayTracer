interface Hittable {
    fun hit(ray: Ray, tMin: Double = 0.001, tMax: Double = Double.MAX_VALUE): HitRecord?
}