package main

import "fmt"

func voidFunction(someValue string) {
	fmt.Printf(someValue)
}

func singleValueFuction(a int, b int) int {
	return a + b
}

func multipleValuesFunc(a int, b int) (int, bool) {
	return a * b, true
}

func main() {
	voidFunction("voidFunction() -  This function return nothihg!\n")
	fmt.Printf("singleValueFuction() - %d\n", singleValueFuction(1, 2))
	// Looking only for the numeric value
	result, _ := multipleValuesFunc(2, 2)
	fmt.Printf("multipleValuesFunc() - %d\n", result)
}
