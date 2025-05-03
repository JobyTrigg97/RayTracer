class Ray(val origin: Vector3D, val direction: Vector3D){

    fun rayColor(world: Scene): Vector3D {
        val rec = world.hit(this)
        return if (rec != null) {
            Vector3D(rec.normal.x + 1, rec.normal.y + 1, rec.normal.z + 1) * 0.5
        } else {
            val unitDir = direction.unit()
            val t = 0.5 * (unitDir.y + 1.0)
            Vector3D(1.0, 1.0, 1.0) * (1.0 - t) + Vector3D(0.5, 0.7, 1.0) * t
        }
    }
}