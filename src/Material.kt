interface Material {
    fun scatter(rayIn: Ray, hitRecord: HitRecord): Pair<Ray, Vector3D>?
}