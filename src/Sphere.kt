import kotlin.math.sqrt

class Sphere (val center: Vector3D, val radius: Double) :Hittable {

    override fun hit(ray: Ray, tMin: Double, tMax: Double): HitRecord? {
        //r(t) = o +td
        // quadratic in t = (a)t +2(b)t+(c)=0.
        val centerToRayOrigin = ray.origin - center

        val directionLengthSquared = ray.direction.dot(ray.direction)

        val originToCenterDotDirection = centerToRayOrigin.dot(ray.direction)

        val centerOffsetLengthSquared = centerToRayOrigin.dot(centerToRayOrigin)
        val radiusSquared = radius * radius

        val discriminant = originToCenterDotDirection * originToCenterDotDirection -
                directionLengthSquared * (centerOffsetLengthSquared - radiusSquared)

        if (discriminant < 0) return null
        val sqrtOfDiscriminant = sqrt(discriminant)
        var root = (-originToCenterDotDirection - sqrtOfDiscriminant) / directionLengthSquared
        if (root < tMin || root > tMax)
            root = (-originToCenterDotDirection + sqrtOfDiscriminant) / directionLengthSquared
            if (root < tMin || root > tMax) return null

        val hitPoint = ray.origin + ray.direction * root
        val vectorFromCenterToHitPoint = (hitPoint - center)
        val surfaceNormal = vectorFromCenterToHitPoint / radius
        return HitRecord(root, hitPoint, surfaceNormal.unit())
    }
}