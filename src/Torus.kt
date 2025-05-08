// Torus.kt
import kotlin.math.abs
import kotlin.math.hypot

/**
 *
 * @param center            World-space centre of the torus.
 * @param axisDirection     Unit axis around which the tube is revolved
 *                          (e.g. Vector3D(0,1,0) -> ring lies in XZ-plane).
 * @param majorRadius       Distance from centre to middle of the tube (R).
 * @param minorRadius       Radius of the tube itself (r).
 * @param material          Surface material (Lambertian or Metal).
 * @param maxMarchSteps     How many SDF steps per ray (you can bump this for higher precision).
 * @param hitThreshold      Distance threshold to consider “hit” (world units).
 */
class Torus(
    private val center: Vector3D,
    private val axisDirection: Vector3D,
    private val majorRadius: Double,
    private val minorRadius: Double,
    private val material: Material,
    private val maxMarchSteps: Int = 100,
    private val hitThreshold: Double = 1e-4
) : Hittable {
    // Build an orthonormal basis [u, v, w] with w = axisDirection.unit()
    private val axisUnit = axisDirection.unit()
    private val basisU   = if (abs(axisUnit.x) < 0.1)
        axisUnit.cross(Vector3D(1.0, 0.0, 0.0)).unit()
    else
        axisUnit.cross(Vector3D(0.0, 1.0, 0.0)).unit()
    private val basisV   = axisUnit.cross(basisU)

    override fun hit(ray: Ray, tMin: Double, tMax: Double): HitRecord? {
        var t = tMin
        // march along the ray
        for (step in 0 until maxMarchSteps) {
            if (t > tMax) break
            val p = ray.origin + ray.direction * t
            val dist = signedDistance(p)
            if (dist.isNaN()) return null     // guard against bad SDF
            if (dist < hitThreshold) {
                // we’re on the surface—compute outward normal
                val n = estimateNormal(p).unit()
                return HitRecord(
                    t             = t,
                    point         = p,
                    surfaceNormal = n,
                    material      = material
                )
            }
            t += dist
        }
        return null
    }

    /** World -> local transform and torus SDF: |(sqr(x^2+y^2)−R, z)| − r. */
    private fun signedDistance(worldPt: Vector3D): Double {
        val local = worldToLocal(worldPt)
        val radial = hypot(local.x, local.y) - majorRadius
        return hypot(radial, local.z) - minorRadius
    }

    /** Approximate SDF via finite differences in local space. */
    private fun estimateNormal(worldPt: Vector3D): Vector3D {
        val h = 1e-5
        val dx = signedDistance(worldPt + Vector3D(h,0.0,0.0)) -
                signedDistance(worldPt - Vector3D(h,0.0,0.0))
        val dy = signedDistance(worldPt + Vector3D(0.0,h,0.0)) -
                signedDistance(worldPt - Vector3D(0.0,h,0.0))
        val dz = signedDistance(worldPt + Vector3D(0.0,0.0,h)) -
                signedDistance(worldPt - Vector3D(0.0,0.0,h))
        return Vector3D(dx, dy, dz)
    }

    /** Map a world point into the torus’s local (u,v,w) frame. */
    private fun worldToLocal(worldPt: Vector3D): Vector3D {
        val p = worldPt - center
        return Vector3D(
            x = p.dot(basisU),
            y = p.dot(basisV),
            z = p.dot(axisUnit)
        )
    }
}
