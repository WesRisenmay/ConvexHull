import matplotlib.pyplot as plt
import random

def plot(points, convex_hull, max):

    for pt in points:
        plt.plot(pt[0], pt[1], 'ro')
    for pt in convex_hull:
        plt.plot(pt[0], pt[1], 'bo')

    plt.axis([-2, max, -2, max])
    plt.show()

def generate_points(min, max, total):
    points = [[random.choice(range(min,max)), random.choice(range(min,max))] for i in range(total)]
    return points

def brute_force(points):
    convex_hull = []
    for pt_a in points:
        for pt_b in points:
            on_hull = True
            prev_cross_prod = None
            if pt_a != pt_b:
                for pt_c in points:
                    if pt_c != pt_a and pt_c != pt_b:
                        v1 = [pt_a[0] - pt_b[0], pt_a[1] - pt_b[1]]
                        v2 = [pt_b[0] - pt_c[0], pt_b[1] - pt_c[1]]
                        cross_prod = v1[0]*v2[1] - v2[0]*v1[1]
                        if cross_prod > 0:
                            cross_prod = 1
                        else:
                            cross_prod = -1
                        if cross_prod == prev_cross_prod or type(prev_cross_prod) is type(None) or abs(cross_prod) == 0:
                            prev_cross_prod = cross_prod
                        else:
                            on_hull = False
                            break
                if on_hull and (pt_a not in convex_hull):
                    convex_hull.append(pt_a)
                if on_hull and (pt_b not in convex_hull):
                    convex_hull.append(pt_b)
    return convex_hull

def quick_hull(points):
    """
    Under average circumstances the algorithm works quite well, but processing usually becomes slow in cases of high symmetry or points lying on the circumference of a circle. The algorithm can be broken down to the following steps:
    (1) Find the points with minimum and maximum x coordinates, those are bound to be part of the convex.
    (2) Use the line formed by the two points to divide the set in two subsets of points, which will be processed recursively.
    (3) Determine the point, on one side of the line, with the maximum distance from the line. The two points found before along with this one form a triangle.
    (4) The points lying inside of that triangle cannot be part of the convex hull and can therefore be ignored in the next steps.
    (5) Repeat the previous two steps on the two lines formed by the triangle (not the initial line).
    (6) Keep on doing so on until no more points are left, the recursion has come to an end and the points selected constitute the convex hull.
    """
    hull = []
    def max_min(points):
        min_x_pt = points[0]
        max_x_pt = points[0]
        for pt in points:
            if pt[0] > max_x_pt[0]:
                max_x_pt = pt
            elif pt[0] < min_x_pt[0]:
                min_x_pt = pt
        return [max_x_pt, min_x_pt]

    def triangle():
        print 'foo'

    max_min(points)


if __name__ == '__main__':
    min_val = 0
    max_val = 1000
    total_points = 33

    points = generate_points(min_val, max_val, total_points)
    #quick_hull(points)
    hull_pts = brute_force(points)
    plot(points, hull_pts, max_val)

