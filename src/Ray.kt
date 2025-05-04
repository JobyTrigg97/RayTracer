class Ray(val origin: Vector3D, val direction: Vector3D){

    fun rayColor(world: Scene): Vector3D {
        val hitRecord = world.hit(this)
        return if (hitRecord != null) {
            Vector3D(hitRecord.surfaceNormal.x + 1, hitRecord.surfaceNormal.y + 1, hitRecord.surfaceNormal.z + 1) * 0.5
        } else {
            val unitDir = direction.unit()
            val t = 0.5 * (unitDir.y + 1.0)
            Vector3D(1.0, 1.0, 1.0) * (1.0 - t) + Vector3D(0.5, 0.7, 1.0) * t
        }
    }
}