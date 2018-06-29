
from collections import namedtuple
from math import ceil

Result = namedtuple('Result', 'index interations not_found')

def main():
    sorted_array  = [1, 20, 30, 40, 50, 60]
    result = _search(first = 0,
                   last = len(sorted_array) - 1,
                   sorted_array  = sorted_array,
                   searched_item = 1,
                   counter = 0)

    if result.not_found:
        print("item not found with {interations}".format(interations = result.interations))
    else:
        print("Found at index {index} with {interations} interations".format(index = result.index, interations = result.interations))

def _search(first, last, sorted_array, searched_item, counter):
    middle = ceil((first + last) / 2)
    counter +=  1

    if first > last:
        return Result(None, counter, True)
    elif sorted_array[middle] == searched_item:
        return Result(middle, counter, False)
    elif sorted_array[middle] > searched_item:
        return _search(first = first,
                       last = middle - 1,
                       sorted_array =  sorted_array,
                       searched_item = searched_item,
                       counter = counter)
    else:
        return _search(first = middle + 1,
                       last =  last,
                       sorted_array = sorted_array,
                       searched_item = searched_item,
                       counter = counter)

if __name__== "__main__":
  main()
