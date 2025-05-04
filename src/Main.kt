import java.io.File
import kotlin.math.sqrt
import kotlin.random.Random

fun randomDouble(): Double = Random.nextDouble()

fun main() {
    val imageWidth = 800
    val imageHeight = 400
    val aspectRatio = imageWidth.toDouble() / imageHeight.toDouble()

    val samplesPerPixel = 100
    val maxDepth = 50

    val viewportHeight = 2.0
    val viewportWidth = aspectRatio * viewportHeight
    val focalLength = 1.0

    val origin = Vector3D(0.0, 0.0, 0.0)
    val horizontal = Vector3D(viewportWidth, 0.0, 0.0)
    val vertical = Vector3D(0.0, viewportHeight, 0.0)
    val lowerLeft = origin - horizontal/2.0 - vertical/2.0 - Vector3D(0.0, 0.0, focalLength)

    val world = Scene(listOf(
        Sphere(Vector3D(0.0,0.0,-1.0), 0.5, Metal(Vector3D(0.1,0.1, 1.0), 0.5)),
        Sphere(Vector3D(0.0,-100.5, -1.0), 100.0, Lambertian(Vector3D(0.1, 0.8, 0.1))),
    ))

    //PPM header
    val out = StringBuilder().apply {
        append("P3\n$imageWidth $imageHeight\n255\n")
    }

    for (j in imageHeight - 1 downTo 0) {
        for (i in 0 until imageWidth) {
            var pixelColorSum = Vector3D(0.0, 0.0, 0.0)

            repeat(samplesPerPixel) {
                val u = (i + randomDouble()) / (imageWidth - 1)
                val v = (j + randomDouble()) / (imageHeight - 1)
                val direction = lowerLeft + horizontal * u + vertical * v - origin
                val ray = Ray(origin, direction)
                pixelColorSum += ray.rayColor(world, maxDepth)
            }
            val scale = 1.0/samplesPerPixel
            val averagedColor = pixelColorSum * scale
            val r = (255.999 * sqrt(averagedColor.x)).toLong()
            val g = (255.999 * sqrt(averagedColor.y)).toInt()
            val b = (255.999 * sqrt(averagedColor.z)).toInt()
            out.append("$r $g $b ")
        }
        out.append("\n")
    }
    File("output.ppm").writeText(out.toString())
    println("Rendered to output.ppm with anti-aliasing ($samplesPerPixel samples/pixel)")
}