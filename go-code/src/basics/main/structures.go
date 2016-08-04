package main

import "fmt"

type Person struct {
	Name string
	CPF  int
	*Address
}

type Address struct {
	Street     string
	Number     int
	Complement string
}

func (address *Address) letterAddress() string {
	return fmt.Sprint(address.Street, " ", address.Number, " ", address.Complement)
}

// contructors, like a factory
func NewPerson(name string, CPF int) Person {
	return Person{
		Name: name,
		CPF:  CPF,
	}
}

// more verbose constructor
func buildJhon() *Person {
	jhon := new(Person)
	jhon.Name = "Jhon"
	jhon.CPF = 789
	jhon.Address = &Address{"XXX St", 123, "Nothing"}
	return jhon
}

func main() {

	Jack := Person{
		Name: "Jack",
		CPF:  9000,
	}

	Bryan := Person{"Bryan", 456, &Address{"YYYY, St", 345, "Nonwhere"}}
	emptyPerson := Person{}

	fmt.Printf("EmptyPerson() %s\n", emptyPerson.Name)
	fmt.Printf("Bryan %s\n", Bryan.Name)
	fmt.Printf("Jack %s\n", Jack.Name)
	fmt.Printf("Jhon %s\n", buildJhon().Name)
	fmt.Printf("Jhon adress %s\n", buildJhon().Name)
	// buildJhon().Address.letterAddress would work too. This is similar to mixins in other languages
	fmt.Printf("Full Jhon Address %s\n", buildJhon().letterAddress())
}
