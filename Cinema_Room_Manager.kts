fun show(numR:Int, numS:Int, cinemaL:MutableList<MutableList<String>>)  {
    println("Cinema:")
    print(" ")
    for(i in 1..numS){
        print(" $i")
    }
    println()

    for (r in 1..numR) {
        print("$r ")
        println(cinemaL[r-1].joinToString(" "))
    }
}

fun buy(numR:Int, numS:Int, cinemaL:MutableList<MutableList<String>>): MutableList<MutableList<String>> {

    println()
    println("Enter a row number: ")
    var userRow = readln().toInt()
    println("Enter a seat number in that row: ")
    var userSeat = readln().toInt()

    // если введенные координаты места вне допустимого диапазона
    if ((userRow > numR || userRow <= 0) || (userSeat > numS || userSeat <= 0)) {
        throw IndexOutOfBoundsException("Wrong input!")
    }

    // если введенные координаты места уже заняты другим пользователем
    if (cinemaL[userRow - 1][userSeat - 1] == "B") {
        throw RuntimeException("That ticket has already been purchased!")
    }

    var userTicket = getOneTicket(numR, numS, userRow)
    println()
    println("Ticket price: $$userTicket")
    println()
    cinemaL[userRow - 1][userSeat - 1] = "B"

    return cinemaL
}

fun getOneTicket(numR:Int, numS:Int, userRow: Int) : Int{
    val oneTicketExpencive = 10
    val oneTicketСheap = 8

    if (numR * numS <= 60 || userRow <= numR / 2) {
        return oneTicketExpencive
    }

    return oneTicketСheap
}

fun calculateStatistics (numR:Int, numS:Int, cinemaL:MutableList<MutableList<String>>) {
    // показать количество купленных билетов
    var purchasedSeats = calculatePurchasedSeats(numR, numS, cinemaL)
    println("Number of purchased tickets: $purchasedSeats")

    // количество купленных билетов в %
    var formatPercentage = "%.2f".format(calculatePercentage(numR, numS, cinemaL))
    println("Percentage: $formatPercentage%")

    // текущий доход
    var currentIncome = getCurrentIncome(numR, numS, cinemaL)
    println("Current income: $$currentIncome")

    // доход с полностью выкупленного зала
    var totalIncome = getTotalIncome(numR, numS)
    println("Total income: $$totalIncome")
}

fun calculatePurchasedSeats(numR:Int, numS:Int, cinemaL:MutableList<MutableList<String>>): Int {
    var purchasedSeats = 0

    for (r in 1..numR) {
        for (s in 1..numS) {
            if (cinemaL[r - 1][s - 1] == "B") {
                purchasedSeats ++
            }
        }
    }

    return purchasedSeats
}

fun calculatePercentage(numR:Int, numS:Int, cinemaL:MutableList<MutableList<String>>): Double {
    var totalNumberSeats = numR * numS

    return calculatePurchasedSeats(numR, numS, cinemaL).toDouble() * 100 / totalNumberSeats
}

fun getCurrentIncome(numR:Int, numS:Int, cinemaL:MutableList<MutableList<String>>): Int {
    var currentIncome = 0

    for (r in 1..numR) {
        for (s in 1..numS) {
            if (cinemaL[r - 1][s - 1] == "B") {
                currentIncome = currentIncome + getOneTicket(numR, numS, r)
            }
        }
    }

    return currentIncome
}

fun getTotalIncome(numR:Int, numS:Int): Int {
    var totalIncome = 0

    for (r in 1..numR) {
        for (s in 1..numS) {
            totalIncome = totalIncome + getOneTicket(numR, numS, r)
        }
    }

    return totalIncome
}

fun main() {
    println("Enter the number of rows: ")
    var numRows = readln().toInt()
    println("Enter the number of seats in each row: ")
    var numSeats = readln().toInt()
    println()

    // заполнение пустого зала
    var cinemaList = mutableListOf<MutableList<String>>()
    for (r in 1..numRows) {
        var rowList = mutableListOf<String>()
        for (s in 1..numSeats) {
            rowList.add("S")
        }
        cinemaList.add(rowList)
    }

    do {
        println()
        println("1. Show the seats")
        println("2. Buy a ticket")
        println("3. Statistics")
        println("0. Exit")

        var userChoice = readln().toInt()

        do {
            var error = false
            try {
                when (userChoice) {
                    1 -> show(numRows, numSeats, cinemaList)
                    2 -> cinemaList = buy(numRows, numSeats, cinemaList)
                    3 -> calculateStatistics(numRows, numSeats, cinemaList)
                }
            } catch (e:IndexOutOfBoundsException) {
                println()
                println(e.message)
                error = true
            } catch (e:RuntimeException) {
                println()
                println(e.message)
                error = true
            }
        } while (error)
   } while (userChoice != 0)
}

main()