package com.subscribe.kmpproject.unit

/**
 * Created on 2023/11/22
 * @author：xiezh
 * @function：
 */
class Kid(private val mother: Mother) {
    var money = 0
        private set

    fun wantMoney() {
        money += mother.giveMoney()
    }
}

class Mother {
    fun giveMoney(): Int {
        return 100
    }
}