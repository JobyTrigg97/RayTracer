import kotlin.math.sqrt

class Sphere (val center: Vector3D, val radius: Double) :Hittable {

    override fun hit(ray: Ray, tMin: Double, tMax: Double): HitRecord? {
        //r(t) = o +td
        // quadratic in t = (a)t +2(b)t+(c)=0.
        val sphereCenterToRayOrigin = ray.origin - center

        val directionLengthSquared = ray.direction.dot(ray.direction)

        val originToCenterDotDirection = sphereCenterToRayOrigin.dot(ray.direction)

        val centerOffsetLengthSquaredMinusRadiusSquared =
            sphereCenterToRayOrigin.dot(sphereCenterToRayOrigin) - radius * radius

        val discriminant = originToCenterDotDirection * originToCenterDotDirection -
                directionLengthSquared * centerOffsetLengthSquaredMinusRadiusSquared

        if (discriminant < 0) return null
        val sqrtd = sqrt(discriminant)
        var root = (-originToCenterDotDirection - sqrtd) / directionLengthSquared
        if (root < tMin || root > tMax)
            root = (-originToCenterDotDirection + sqrtd) / directionLengthSquared
            if (root < tMin || root > tMax) return null

        val hitPoint = ray.origin + ray.direction * root
        val normal = (hitPoint - center) / radius
        return HitRecord(root, hitPoint, normal.unit())
    }
}