package main

import "fmt"

func typeInferenceExample() {
	// := infers the type, which makes the code less verbose
	power := getValue()
	fmt.Printf("typeInferenceExample() - It's over %d\n", power)
}

func getValue() int {
	return 9000
}

func verboseExample() {
	var power int
	power = 9000
	fmt.Printf("verboseExample() -  It's over %d\n", power)
}

func multipleAssigmentsExample() {
	power, name := getValue(), "Jack"
	fmt.Printf("multipleAssigmentsExample() - %s It's over %d\n", name, power)
}

func main() {
	typeInferenceExample()
	multipleAssigmentsExample()
	verboseExample()
}
