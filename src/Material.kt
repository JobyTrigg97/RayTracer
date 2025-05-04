import kotlin.random.Random

interface Material {
    fun scatter(rayIn: Ray, hitRecord: HitRecord): Pair<Ray, Vector3D>?

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