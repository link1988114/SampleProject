var Lottery = {
    createNew: function () {
        var LotObj = {};

        LotObj.wheelHolder = null;
        LotObj.rankingName = [];   // rank names
        LotObj.rankingBonus = [];  // bonus names
        LotObj.ranking = [];   // origin prop
        LotObj.propRanking = [];  //prop for calc
        LotObj.pickIndex = -1;
        LotObj.minBonusIndex = 0;

        LotObj.speedX = 0;
        LotObj.dvX = 0.5;
        LotObj.maxspeedX = 60;
        LotObj.stopDistX = 4000;
        LotObj.stopFlag = false;


        LotObj.createLotteryWheel = function (wheelHolderSelector, rankingNameArr, bonusArr, rankingArr, minBonusIndex) {
            this.rankingName = rankingNameArr;
            this.rankingBonus = bonusArr;
            this.setRanking(rankingArr);
            this.minBonusIndex = minBonusIndex;
            this.wheelHolder = document.getElementById(wheelHolderSelector.replace("#", ""));
        };

        LotObj.setRanking = function (arr) {
            this.ranking = arr;
            this.propRanking = getPropRanking(this.ranking);
            this.pickIndex = arr.length - 1;
        };

        LotObj.startWheelPick = function () {
            initWheel();
            this.pickIndex = getPickIndex(this.propRanking);

            //document.getElementById("showdiv").innerHTML = this.propRanking.toString() +"@@"+ this.pickIndex;

            if (this.pickIndex == -1) {
                this.pickIndex = this.minBonusIndex;
            }

            //alert(this.rankingName[this.pickIndex]);

            var rndDist = 360.0 / this.ranking.length * (this.pickIndex + 1);
            var whiteSpace = 360.0 / this.ranking.length * 0.1;
            var baseDist = 11880 + ((360.0 - this.ranking.length * 2 * whiteSpace) / this.ranking.length) * Math.random() + whiteSpace;
            var totalDist = baseDist + rndDist;
            var start = 0;

            var timer = setInterval(function () {
                LotObj.speedX += LotObj.dvX;
                if (LotObj.speedX > LotObj.maxspeedX) {
                    LotObj.speedX = LotObj.maxspeedX;
                }
                if (LotObj.speedX <= 0) {
                    LotObj.speedX = 0;
                }

                var deg = start + LotObj.speedX;
                start += LotObj.speedX;
                rotateObj(LotObj.wheelHolder, deg);

                if (totalDist - start <= LotObj.stopDistX && LotObj.stopFlag == false) {
                    LotObj.stopFlag = true;
                    LotObj.dvX = -1.0 * LotObj.maxspeedX / (2.0 * (totalDist - start) / LotObj.maxspeedX + 1);
                }
                if (LotObj.speedX == 0) {
                    rotateObj(LotObj.wheelHolder, totalDist);
                    clearInterval(timer);
                    LotObj.showBounsHint();
                }
            }, 20);
        };


        LotObj.startWheelPick2 = function (pick) {
            initWheel();
            this.pickIndex = pick; //getPickIndex(this.propRanking);

            //document.getElementById("showdiv").innerHTML = this.propRanking.toString() +"@@"+ this.pickIndex;

            if (this.pickIndex == -1) {
                this.pickIndex = this.minBonusIndex;
            }

            //alert(this.rankingName[this.pickIndex]);

            var rndDist = 360.0 / this.ranking.length * (this.pickIndex + 1);
            var whiteSpace = 360.0 / this.ranking.length * 0.1;
            var baseDist = 11880 + ((360.0 - this.ranking.length * 2 * whiteSpace) / this.ranking.length) * Math.random() + whiteSpace;
            var totalDist = baseDist + rndDist;
            var start = 0;

            var timer = setInterval(function () {
                LotObj.speedX += LotObj.dvX;
                if (LotObj.speedX > LotObj.maxspeedX) {
                    LotObj.speedX = LotObj.maxspeedX;
                }
                if (LotObj.speedX <= 0) {
                    LotObj.speedX = 0;
                }

                var deg = start + LotObj.speedX;
                start += LotObj.speedX;
                rotateObj(LotObj.wheelHolder, deg);

                if (totalDist - start <= LotObj.stopDistX && LotObj.stopFlag == false) {
                    LotObj.stopFlag = true;
                    LotObj.dvX = -1.0 * LotObj.maxspeedX / (2.0 * (totalDist - start) / LotObj.maxspeedX + 1);
                }
                if (LotObj.speedX == 0) {
                    rotateObj(LotObj.wheelHolder, totalDist);
                    clearInterval(timer);
                    LotObj.showBounsHint();
                }
            }, 20);
        };


        LotObj.showBounsHint = function () {
        };



        /////////////////////////////////////////////////////////////

        function getPropRanking(arr) {
            if (arr.length <= 1) {
                return [1];
            }
            var sum = 0;
            for (var i = 0; i < arr.length; i++) {
                sum += arr[i];
            }
            var out = [];
            var itemSum = 0;
            for (var i = 0; i < arr.length; i++) {
                var calc = arr[i] * 1.0 / sum;
                itemSum += calc;
                out.push(itemSum);
            }
            return out;
        }

        function getPickIndex(propRanking) {
            var rnd = Math.random();
            for (var i = 0; i < propRanking.length; i++) {
                if (propRanking[i] >= rnd) {
                    return i;
                }
            }
            return -1;
        }


        function initWheel() {
            LotObj.speedX = 0;
            LotObj.dvX = 0.5;
            LotObj.stopFlag = false;
        }

        function rotateObj(obj, deg) {
            obj.style.transform = 'rotate(' + deg + 'deg)';
        }

        ////////////////////////////////////////////////////////////

        return LotObj;
    }
};