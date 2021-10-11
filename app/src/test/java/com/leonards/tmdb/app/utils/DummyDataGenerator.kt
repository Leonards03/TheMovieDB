package com.leonards.tmdb.app.utils

import androidx.paging.PagingData
import com.leonards.tmdb.core.data.mapper.mapToEntity
import com.leonards.tmdb.core.data.source.remote.response.CreditsResponse
import com.leonards.tmdb.core.data.source.remote.response.ListResponse
import com.leonards.tmdb.core.data.source.remote.response.MovieResponse
import com.leonards.tmdb.core.data.source.remote.response.TvShowResponse
import com.leonards.tmdb.core.domain.model.Movie
import com.leonards.tmdb.core.domain.model.TvShow
import kotlinx.coroutines.flow.flow

object DummyDataGenerator {
    private val movies = arrayListOf(
        Movie(
            movieId = 337404,
            title = "Cruella",
            backdrop = "/6MKr3KgOLmzOP6MSuZERO41Lpkt.jpg",
            poster = "/A0knvX7rlwTyZSKj8H5NiARb45.jpg",
            genres = "",
            overview = "In 1970s London amidst the punk rock revolution, a young grifter named Estella is determined to make a name for herself with her designs. She befriends a pair of young thieves who appreciate her appetite for mischief, and together they are able to build a life for themselves on the London streets. One day, Estella’s flair for fashion catches the eye of the Baroness von Hellman, a fashion legend who is devastatingly chic and terrifyingly haute. But their relationship sets in motion a course of events and revelations that will cause Estella to embrace her wicked side and become the raucous, fashionable and revenge-bent Cruella.",
            voteAverage = 8.7,
            runtime = 0,
            releaseDate = "2021-05-26",
            director = ""
        ),
        Movie(
            movieId = 632357,
            title = "The Unholy",
            backdrop = "/jw6ASGRT2gi8EjCImpGtbiJ9NQ9.jpg",
            poster = "/bShgiEQoPnWdw4LBrYT5u18JF34.jpg",
            genres = "",
            overview = "Alice, a young hearing-impaired girl who, after a supposed visitation from the Virgin Mary, is inexplicably able to hear, speak and heal the sick. As word spreads and people from near and far flock to witness her miracles, a disgraced journalist hoping to revive his career visits the small New England town to investigate. When terrifying events begin to happen all around, he starts to question if these phenomena are the works of the Virgin Mary or something much more sinister.",
            voteAverage = 7.1,
            runtime = 0,
            releaseDate = "2021-03-31",
            director = ""
        ),
        Movie(
            movieId = 637649,
            title = "Wrath of Man",
            backdrop = "/77tui163estZrQ78NBggqDB4n2C.jpg",
            poster = "/YxopfHpsCV1oF8CZaL4M3Eodqa.jpg",
            genres = "",
            overview = "A cold and mysterious new security guard for a Los Angeles cash truck company surprises his co-workers when he unleashes precision skills during a heist. The crew is left wondering who he is and where he came from. Soon, the marksman's ultimate motive becomes clear as he takes dramatic and irrevocable steps to settle a score.",
            voteAverage = 7.9,
            runtime = 0,
            releaseDate = "2021-04-22",
            director = ""
        ),
        Movie(
            movieId = 503736,
            title = "Army of the Dead",
            backdrop = "/9WlJFhOSCPnaaSmsrv0B4zA8iUb.jpg",
            poster = "/z8CExJekGrEThbpMXAmCFvvgoJR.jpg",
            genres = "",
            overview = "Following a zombie outbreak in Las Vegas, a group of mercenaries take the ultimate gamble: venturing into the quarantine zone to pull off the greatest heist ever attempted.",
            voteAverage = 6.6,
            runtime = 0,
            releaseDate = "2021-05-14",
            director = ""
        ),
        Movie(
            movieId = 460465,
            title = "Mortal Kombat",
            backdrop = "/9yBVqNruk6Ykrwc32qrK2TIE5xw.jpg",
            poster = "/nkayOAUBUu4mMvyNf9iHSUiPjF1.jpg",
            genres = "",
            overview = "Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe.",
            voteAverage = 7.6,
            runtime = 0,
            releaseDate = "2021-04-07",
            director = ""
        ),
        Movie(
            movieId = 399566,
            title = "Godzilla vs. Kong",
            backdrop = "/inJjDhCjfhh3RtrJWBmmDqeuSYC.jpg",
            poster = "/pgqgaUx1cJb5oZQQ5v0tNARCeBp.jpg",
            genres = "",
            overview = "In a time when monsters walk the Earth, humanity’s fight for its future sets Godzilla and Kong on a collision course that will see the two most powerful forces of nature on the planet collide in a spectacular battle for the ages.",
            voteAverage = 8.1,
            runtime = 0,
            releaseDate = "2021-03-24",
            director = ""
        ),
        Movie(
            movieId = 578701,
            title = "Those Who Wish Me Dead",
            backdrop = "/ouOojiypBE6CD1aqcHPVq7cJf2R.jpg",
            poster = "/xCEg6KowNISWvMh8GvPSxtdf9TO.jpg",
            genres = "",
            overview = "A young boy finds himself pursued by two assassins in the Montana wilderness with a survival expert determined to protecting him - and a forest fire threatening to consume them all.",
            voteAverage = 7.0,
            runtime = 0,
            releaseDate = "2021-05-05",
            director = ""
        ),
        Movie(
            movieId = 808023,
            title = "The Virtuoso",
            backdrop = "/efrdAWS63s8TTWdrI2uNdIhn1dj.jpg",
            poster = "/vXHzO26mJaOt4VO7ZFiM6No5ScT.jpg",
            genres = "",
            overview = "A lonesome stranger with nerves of steel must track down and kill a rogue hitman to satisfy an outstanding debt. But the only information he's been given is a time and location where to find his quarry. No name. No description. Nothing.",
            voteAverage = 6.2,
            runtime = 0,
            releaseDate = "2021-04-30",
            director = ""
        ),
        Movie(
            movieId = 635302,
            title = "Demon Slayer -Kimetsu no Yaiba- The Movie: Mugen Train",
            backdrop = "/xPpXYnCWfjkt3zzE0dpCNME1pXF.jpg",
            poster = "/h8Rb9gBr48ODIwYUttZNYeMWeUU.jpg",
            genres = "",
            overview = "Tanjirō Kamado, joined with Inosuke Hashibira, a boy raised by boars who wears a boar's head, and Zenitsu Agatsuma, a scared boy who reveals his true power when he sleeps, boards the Infinity Train on a new mission with the Fire Hashira, Kyōjurō Rengoku, to defeat a demon who has been tormenting the people and killing the demon slayers who oppose it!",
            voteAverage = 8.4,
            runtime = 0,
            releaseDate = "2020-10-16",
            director = ""
        ),
        Movie(
            movieId = 520763,
            title = "A Quiet Place Part II",
            backdrop = "/z2UtGA1WggESspi6KOXeo66lvLx.jpg",
            poster = "/4q2hz2m8hubgvijz8Ez0T2Os2Yv.jpg",
            genres = "",
            overview = "Following the events at home, the Abbott family now face the terrors of the outside world. Forced to venture into the unknown, they realize that the creatures that hunt by sound are not the only threats that lurk beyond the sand path.",
            voteAverage = 7.4,
            runtime = 0,
            releaseDate = "2021-05-21",
            director = ""
        ),
        Movie(
            movieId = 615457,
            title = "Nobody",
            backdrop = "/6zbKgwgaaCyyBXE4Sun4oWQfQmi.jpg",
            poster = "/oBgWY00bEFeZ9N25wWVyuQddbAo.jpg",
            genres = "",
            overview = "Hutch Mansell, a suburban dad, overlooked husband, nothing neighbor — a \\\"nobody.\\\" When two thieves break into his home one night, Hutch's unknown long-simmering rage is ignited and propels him on a brutal path that will uncover dark secrets he fought to leave behind.",
            voteAverage = 8.5,
            runtime = 0,
            releaseDate = "2021-03-26",
            director = ""
        ),
        Movie(
            movieId = 602734,
            title = "Spiral: From the Book of Saw",
            backdrop = "/7JENyUT8ABxcvrcijDBVpdjgCY9.jpg",
            poster = "/lcyKve7nXRFgRyms9M1bndNkKOx.jpg",
            genres = "",
            overview = "Working in the shadow of an esteemed police veteran, brash Detective Ezekiel “Zeke” Banks and his rookie partner take charge of a grisly investigation into murders that are eerily reminiscent of the city’s gruesome past.  Unwittingly entrapped in a deepening mystery, Zeke finds himself at the center of the killer’s morbid game.",
            voteAverage = 6.7,
            runtime = 0,
            releaseDate = "2021-05-12",
            director = ""
        ),
        Movie(
            movieId = 573680,
            title = "The Banishing",
            backdrop = "/xrAaJAn3hqkInq5kE1AGJqIGXyT.jpg",
            poster = "/xD9mc8JCVXA8T8u4Od7qOUBuGH4.jpg",
            genres = "",
            overview = "In the 1930s, a young reverend and his family are forced to confront their worst fears when they discover their new home holds a horrifying secret.",
            voteAverage = 6.1,
            runtime = 0,
            releaseDate = "2021-04-29",
            director = ""
        ),
        Movie(
            movieId = 647302,
            title = "Benny Loves You",
            backdrop = "/czHYg6LQ5926OL8wj5kC09pNR12.jpg",
            poster = "/mQ8vALvqA0z0qglG3gI6xpVcslo.jpg",
            genres = "",
            overview = "Jack, a man desperate to improve his life, throws away his beloved childhood plush, Benny. It’s a move that has disastrous consequences when Benny springs to life with deadly intentions!",
            voteAverage = 6.0,
            runtime = 0,
            releaseDate = "2019-11-21",
            director = ""
        ),
        Movie(
            movieId = 634528,
            title = "The Marksman",
            backdrop = "/5Zv5KmgZzdIvXz2KC3n0MyecSNL.jpg",
            poster = "/6vcDalR50RWa309vBH1NLmG2rjQ.jpg",
            genres = "",
            overview = "Jim Hanson’s quiet life is suddenly disturbed by two people crossing the US/Mexico border – a woman and her young son – desperate to flee a Mexican cartel. After a shootout leaves the mother dead, Jim becomes the boy’s reluctant defender. He embraces his role as Miguel’s protector and will stop at nothing to get him to safety, as they go on the run from the relentless assassins.",
            voteAverage = 7.4,
            runtime = 0,
            releaseDate = "2021-01-15",
            director = ""
        ),
        Movie(
            movieId = 804435,
            title = "Vanquish",
            backdrop = "/mYM8x2Atv4MaLulaV0KVJWI1Djv.jpg",
            poster = "/AoWY1gkcNzabh229Icboa1Ff0BM.jpg",
            genres = "",
            overview = "Victoria is a young mother trying to put her dark past as a Russian drug courier behind her, but retired cop Damon forces Victoria to do his bidding by holding her daughter hostage. Now, Victoria must use guns, guts and a motorcycle to take out a series of violent gangsters—or she may never see her child again.",
            voteAverage = 6.0,
            runtime = 0,
            releaseDate = "2021-04-16",
            director = ""
        ),
        Movie(
            movieId = 587807,
            title = "Tom & Jerry",
            backdrop = "/9ns9463dwOeo1CK1JU2wirL5Yi1.jpg",
            poster = "/8XZI9QZ7Pm3fVkigWJPbrXCMzjq.jpg",
            genres = "",
            overview = "Tom the cat and Jerry the mouse get kicked out of their home and relocate to a fancy New York hotel, where a scrappy employee named Kayla will lose her job if she can’t evict Jerry before a high-class wedding at the hotel. Her solution? Hiring Tom to get rid of the pesky mouse.",
            voteAverage = 7.3,
            runtime = 0,
            releaseDate = "2021-02-11",
            director = ""
        ),
        Movie(
            movieId = 458576,
            title = "Monster Hunter",
            backdrop = "/z8TvnEVRenMSTemxYZwLGqFofgF.jpg",
            poster = "/1UCOF11QCw8kcqvce8LKOO6pimh.jpg",
            genres = "",
            overview = "A portal transports Cpt. Artemis and an elite unit of soldiers to a strange world where powerful monsters rule with deadly ferocity. Faced with relentless danger, the team encounters a mysterious hunter who may be their only hope to find a way home.",
            voteAverage = 7.0,
            runtime = 0,
            releaseDate = "2020-12-03",
            director = ""
        ),
        Movie(
            movieId = 581387,
            title = "Ashfall",
            backdrop = "/h9DIlghaiTxbQbt1FIwKNbQvEL.jpg",
            poster = "/zoeKREZ2IdAUnXISYCS0E6H5BVh.jpg",
            genres = "",
            overview = "A group of unlikely heroes from across the Korean peninsula try to save the day after a volcano erupts on the mythical and majestic Baekdu Mountain.",
            voteAverage = 6.4,
            runtime = 0,
            releaseDate = "2019-12-19",
            director = ""
        ),
        Movie(
            movieId = 423108,
            title = "The Conjuring: The Devil Made Me Do It",
            backdrop = "/qi6Edc1OPcyENecGtz8TF0DUr9e.jpg",
            poster = "/xbSuFiJbbBWCkyCCKIMfuDCA4yV.jpg",
            genres = "",
            overview = "Paranormal investigators Ed and Lorraine Warren encounter what would become one of the most sensational cases from their files. The fight for the soul of a young boy takes them beyond anything they'd ever seen before, to mark the first time in U.S. history that a murder suspect would claim demonic possession as a defense.",
            voteAverage = 8.5,
            runtime = 0,
            releaseDate = "2021-05-25",
            director = ""
        ),

        )

    private val moviesResponse = arrayListOf(
        MovieResponse(
            id = 337404,
            title = "Cruella",
            backdrop = "/6MKr3KgOLmzOP6MSuZERO41Lpkt.jpg",
            poster = "/A0knvX7rlwTyZSKj8H5NiARb45.jpg",
            genres = arrayListOf(),
            overview = "In 1970s London amidst the punk rock revolution, a young grifter named Estella is determined to make a name for herself with her designs. She befriends a pair of young thieves who appreciate her appetite for mischief, and together they are able to build a life for themselves on the London streets. One day, Estella’s flair for fashion catches the eye of the Baroness von Hellman, a fashion legend who is devastatingly chic and terrifyingly haute. But their relationship sets in motion a course of events and revelations that will cause Estella to embrace her wicked side and become the raucous, fashionable and revenge-bent Cruella.",
            voteAverage = 8.7,
            runtime = 0,
            releaseDate = "2021-05-26",
            credits = CreditsResponse(arrayListOf())
        ),
        MovieResponse(
            id = 632357,
            title = "The Unholy",
            backdrop = "/jw6ASGRT2gi8EjCImpGtbiJ9NQ9.jpg",
            poster = "/bShgiEQoPnWdw4LBrYT5u18JF34.jpg",
            genres = arrayListOf(),
            overview = "Alice, a young hearing-impaired girl who, after a supposed visitation from the Virgin Mary, is inexplicably able to hear, speak and heal the sick. As word spreads and people from near and far flock to witness her miracles, a disgraced journalist hoping to revive his career visits the small New England town to investigate. When terrifying events begin to happen all around, he starts to question if these phenomena are the works of the Virgin Mary or something much more sinister.",
            voteAverage = 7.1,
            runtime = 0,
            releaseDate = "2021-03-31",
            credits = CreditsResponse(arrayListOf())
        ),
        MovieResponse(
            id = 637649,
            title = "Wrath of Man",
            backdrop = "/77tui163estZrQ78NBggqDB4n2C.jpg",
            poster = "/YxopfHpsCV1oF8CZaL4M3Eodqa.jpg",
            genres = arrayListOf(),
            overview = "A cold and mysterious new security guard for a Los Angeles cash truck company surprises his co-workers when he unleashes precision skills during a heist. The crew is left wondering who he is and where he came from. Soon, the marksman's ultimate motive becomes clear as he takes dramatic and irrevocable steps to settle a score.",
            voteAverage = 7.9,
            runtime = 0,
            releaseDate = "2021-04-22",
            credits = CreditsResponse(arrayListOf())
        ),
        MovieResponse(
            id = 503736,
            title = "Army of the Dead",
            backdrop = "/9WlJFhOSCPnaaSmsrv0B4zA8iUb.jpg",
            poster = "/z8CExJekGrEThbpMXAmCFvvgoJR.jpg",
            genres = arrayListOf(),
            overview = "Following a zombie outbreak in Las Vegas, a group of mercenaries take the ultimate gamble: venturing into the quarantine zone to pull off the greatest heist ever attempted.",
            voteAverage = 6.6,
            runtime = 0,
            releaseDate = "2021-05-14",
            credits = CreditsResponse(arrayListOf())
        ),
        MovieResponse(
            id = 460465,
            title = "Mortal Kombat",
            backdrop = "/9yBVqNruk6Ykrwc32qrK2TIE5xw.jpg",
            poster = "/nkayOAUBUu4mMvyNf9iHSUiPjF1.jpg",
            genres = arrayListOf(),
            overview = "Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe.",
            voteAverage = 7.6,
            runtime = 0,
            releaseDate = "2021-04-07",
            credits = CreditsResponse(arrayListOf())
        ),
        MovieResponse(
            id = 399566,
            title = "Godzilla vs. Kong",
            backdrop = "/inJjDhCjfhh3RtrJWBmmDqeuSYC.jpg",
            poster = "/pgqgaUx1cJb5oZQQ5v0tNARCeBp.jpg",
            genres = arrayListOf(),
            overview = "In a time when monsters walk the Earth, humanity’s fight for its future sets Godzilla and Kong on a collision course that will see the two most powerful forces of nature on the planet collide in a spectacular battle for the ages.",
            voteAverage = 8.1,
            runtime = 0,
            releaseDate = "2021-03-24",
            credits = CreditsResponse(arrayListOf())
        ),
        MovieResponse(
            id = 578701,
            title = "Those Who Wish Me Dead",
            backdrop = "/ouOojiypBE6CD1aqcHPVq7cJf2R.jpg",
            poster = "/xCEg6KowNISWvMh8GvPSxtdf9TO.jpg",
            genres = arrayListOf(),
            overview = "A young boy finds himself pursued by two assassins in the Montana wilderness with a survival expert determined to protecting him - and a forest fire threatening to consume them all.",
            voteAverage = 7,
            runtime = 0,
            releaseDate = "2021-05-05",
            credits = CreditsResponse(arrayListOf())
        ),
        MovieResponse(
            id = 808023,
            title = "The Virtuoso",
            backdrop = "/efrdAWS63s8TTWdrI2uNdIhn1dj.jpg",
            poster = "/vXHzO26mJaOt4VO7ZFiM6No5ScT.jpg",
            genres = arrayListOf(),
            overview = "A lonesome stranger with nerves of steel must track down and kill a rogue hitman to satisfy an outstanding debt. But the only information he's been given is a time and location where to find his quarry. No name. No description. Nothing.",
            voteAverage = 6.2,
            runtime = 0,
            releaseDate = "2021-04-30",
            credits = CreditsResponse(arrayListOf())
        ),
        MovieResponse(
            id = 635302,
            title = "Demon Slayer -Kimetsu no Yaiba- The Movie: Mugen Train",
            backdrop = "/xPpXYnCWfjkt3zzE0dpCNME1pXF.jpg",
            poster = "/h8Rb9gBr48ODIwYUttZNYeMWeUU.jpg",
            genres = arrayListOf(),
            overview = "Tanjirō Kamado, joined with Inosuke Hashibira, a boy raised by boars who wears a boar's head, and Zenitsu Agatsuma, a scared boy who reveals his true power when he sleeps, boards the Infinity Train on a new mission with the Fire Hashira, Kyōjurō Rengoku, to defeat a demon who has been tormenting the people and killing the demon slayers who oppose it!",
            voteAverage = 8.4,
            runtime = 0,
            releaseDate = "2020-10-16",
            credits = CreditsResponse(arrayListOf())
        ),
        MovieResponse(
            id = 520763,
            title = "A Quiet Place Part II",
            backdrop = "/z2UtGA1WggESspi6KOXeo66lvLx.jpg",
            poster = "/4q2hz2m8hubgvijz8Ez0T2Os2Yv.jpg",
            genres = arrayListOf(),
            overview = "Following the events at home, the Abbott family now face the terrors of the outside world. Forced to venture into the unknown, they realize that the creatures that hunt by sound are not the only threats that lurk beyond the sand path.",
            voteAverage = 7.4,
            runtime = 0,
            releaseDate = "2021-05-21",
            credits = CreditsResponse(arrayListOf())
        ),
        MovieResponse(
            id = 615457,
            title = "Nobody",
            backdrop = "/6zbKgwgaaCyyBXE4Sun4oWQfQmi.jpg",
            poster = "/oBgWY00bEFeZ9N25wWVyuQddbAo.jpg",
            genres = arrayListOf(),
            overview = "Hutch Mansell, a suburban dad, overlooked husband, nothing neighbor — a \\\"nobody.\\\" When two thieves break into his home one night, Hutch's unknown long-simmering rage is ignited and propels him on a brutal path that will uncover dark secrets he fought to leave behind.",
            voteAverage = 8.5,
            runtime = 0,
            releaseDate = "2021-03-26",
            credits = CreditsResponse(arrayListOf())
        ),
        MovieResponse(
            id = 602734,
            title = "Spiral: From the Book of Saw",
            backdrop = "/7JENyUT8ABxcvrcijDBVpdjgCY9.jpg",
            poster = "/lcyKve7nXRFgRyms9M1bndNkKOx.jpg",
            genres = arrayListOf(),
            overview = "Working in the shadow of an esteemed police veteran, brash Detective Ezekiel “Zeke” Banks and his rookie partner take charge of a grisly investigation into murders that are eerily reminiscent of the city’s gruesome past.  Unwittingly entrapped in a deepening mystery, Zeke finds himself at the center of the killer’s morbid game.",
            voteAverage = 6.7,
            runtime = 0,
            releaseDate = "2021-05-12",
            credits = CreditsResponse(arrayListOf())
        ),
        MovieResponse(
            id = 573680,
            title = "The Banishing",
            backdrop = "/xrAaJAn3hqkInq5kE1AGJqIGXyT.jpg",
            poster = "/xD9mc8JCVXA8T8u4Od7qOUBuGH4.jpg",
            genres = arrayListOf(),
            overview = "In the 1930s, a young reverend and his family are forced to confront their worst fears when they discover their new home holds a horrifying secret.",
            voteAverage = 6.1,
            runtime = 0,
            releaseDate = "2021-04-29",
            credits = CreditsResponse(arrayListOf())
        ),
        MovieResponse(
            id = 647302,
            title = "Benny Loves You",
            backdrop = "/czHYg6LQ5926OL8wj5kC09pNR12.jpg",
            poster = "/mQ8vALvqA0z0qglG3gI6xpVcslo.jpg",
            genres = arrayListOf(),
            overview = "Jack, a man desperate to improve his life, throws away his beloved childhood plush, Benny. It’s a move that has disastrous consequences when Benny springs to life with deadly intentions!",
            voteAverage = 6.0,
            runtime = 0,
            releaseDate = "2019-11-21",
            credits = CreditsResponse(arrayListOf())
        ),
        MovieResponse(
            id = 634528,
            title = "The Marksman",
            backdrop = "/5Zv5KmgZzdIvXz2KC3n0MyecSNL.jpg",
            poster = "/6vcDalR50RWa309vBH1NLmG2rjQ.jpg",
            genres = arrayListOf(),
            overview = "Jim Hanson’s quiet life is suddenly disturbed by two people crossing the US/Mexico border – a woman and her young son – desperate to flee a Mexican cartel. After a shootout leaves the mother dead, Jim becomes the boy’s reluctant defender. He embraces his role as Miguel’s protector and will stop at nothing to get him to safety, as they go on the run from the relentless assassins.",
            voteAverage = 7.4,
            runtime = 0,
            releaseDate = "2021-01-15",
            credits = CreditsResponse(arrayListOf())
        ),
        MovieResponse(
            id = 804435,
            title = "Vanquish",
            backdrop = "/mYM8x2Atv4MaLulaV0KVJWI1Djv.jpg",
            poster = "/AoWY1gkcNzabh229Icboa1Ff0BM.jpg",
            genres = arrayListOf(),
            overview = "Victoria is a young mother trying to put her dark past as a Russian drug courier behind her, but retired cop Damon forces Victoria to do his bidding by holding her daughter hostage. Now, Victoria must use guns, guts and a motorcycle to take out a series of violent gangsters—or she may never see her child again.",
            voteAverage = 6.0,
            runtime = 0,
            releaseDate = "2021-04-16",
            credits = CreditsResponse(arrayListOf())
        ),
        MovieResponse(
            id = 587807,
            title = "Tom & Jerry",
            backdrop = "/9ns9463dwOeo1CK1JU2wirL5Yi1.jpg",
            poster = "/8XZI9QZ7Pm3fVkigWJPbrXCMzjq.jpg",
            genres = arrayListOf(),
            overview = "Tom the cat and Jerry the mouse get kicked out of their home and relocate to a fancy New York hotel, where a scrappy employee named Kayla will lose her job if she can’t evict Jerry before a high-class wedding at the hotel. Her solution? Hiring Tom to get rid of the pesky mouse.",
            voteAverage = 7.3,
            runtime = 0,
            releaseDate = "2021-02-11",
            credits = CreditsResponse(arrayListOf())
        ),
        MovieResponse(
            id = 458576,
            title = "Monster Hunter",
            backdrop = "/z8TvnEVRenMSTemxYZwLGqFofgF.jpg",
            poster = "/1UCOF11QCw8kcqvce8LKOO6pimh.jpg",
            genres = arrayListOf(),
            overview = "A portal transports Cpt. Artemis and an elite unit of soldiers to a strange world where powerful monsters rule with deadly ferocity. Faced with relentless danger, the team encounters a mysterious hunter who may be their only hope to find a way home.",
            voteAverage = 7,
            runtime = 0,
            releaseDate = "2020-12-03",
            credits = CreditsResponse(arrayListOf())
        ),
        MovieResponse(
            id = 581387,
            title = "Ashfall",
            backdrop = "/h9DIlghaiTxbQbt1FIwKNbQvEL.jpg",
            poster = "/zoeKREZ2IdAUnXISYCS0E6H5BVh.jpg",
            genres = arrayListOf(),
            overview = "A group of unlikely heroes from across the Korean peninsula try to save the day after a volcano erupts on the mythical and majestic Baekdu Mountain.",
            voteAverage = 6.4,
            runtime = 0,
            releaseDate = "2019-12-19",
            credits = CreditsResponse(arrayListOf())
        ),
        MovieResponse(
            id = 423108,
            title = "The Conjuring: The Devil Made Me Do It",
            backdrop = "/qi6Edc1OPcyENecGtz8TF0DUr9e.jpg",
            poster = "/xbSuFiJbbBWCkyCCKIMfuDCA4yV.jpg",
            genres = arrayListOf(),
            overview = "Paranormal investigators Ed and Lorraine Warren encounter what would become one of the most sensational cases from their files. The fight for the soul of a young boy takes them beyond anything they'd ever seen before, to mark the first time in U.S. history that a murder suspect would claim demonic possession as a defense.",
            voteAverage = 8.5,
            runtime = 0,
            releaseDate = "2021-05-25",
            credits = CreditsResponse(arrayListOf())
        ),

        )

    private val tvShow =
        arrayListOf(
            TvShow(
                tvShowId = 63174,
                title = "Lucifer",
                backdrop = "/h48Dpb7ljv8WQvVdyFWVLz64h4G.jpg",
                poster = "/4EYPN5mVIhKLfxGruy7Dy41dTVn.jpg",
                overview = "Bored and unhappy as the Lord of Hell, Lucifer Morningstar abandoned his throne and retired to Los Angeles, where he has teamed up with LAPD detective Chloe Decker to take down criminals. But the longer he's away from the underworld, the greater the threat that the worst of humanity could escape.",
                voteAverage = 8.5,
                firstAirDate = "2016-01-25",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = "",
                status = ""
            ),
            TvShow(
                tvShowId = 91557,
                title = "Ragnarok",
                backdrop = "/wu444tM9YBllq9UcBv5TeidO3j3.jpg",
                poster = "/xUtOM1QO4r8w8yeE00QvBdq58N5.jpg",
                overview = "A small Norwegian town experiencing warm winters and violent downpours seems to be headed for another Ragnarök -- unless someone intervenes in time.",
                voteAverage = 8.0,
                firstAirDate = "2020-01-31",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = "",
                status = ""
            ),
            TvShow(
                tvShowId = 60735,
                title = "The Flash",
                backdrop = "/9Jmd1OumCjaXDkpllbSGi2EpJvl.jpg",
                poster = "/lJA2RCMfsWoskqlQhXPSLFQGXEJ.jpg",
                overview = "After a particle accelerator causes a freak storm, CSI Investigator Barry Allen is struck by lightning and falls into a coma. Months later he awakens with the power of super speed, granting him the ability to move through Central City like an unseen guardian angel. Though initially excited by his newfound powers, Barry is shocked to discover he is not the only \"meta-human\" who was created in the wake of the accelerator explosion -- and not everyone is using their new powers for good. Barry partners with S.T.A.R. Labs and dedicates his life to protect the innocent. For now, only a few close friends and associates know that Barry is literally the fastest man alive, but it won't be long before the world learns what Barry Allen has become...The Flash.",
                voteAverage = 7.7,
                firstAirDate = "2014-10-07",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = "",
                status = ""
            ),
            TvShow(
                tvShowId = 71712,
                title = "The Good Doctor",
                backdrop = "/iDbIEpCM9nhoayUDTwqFL1iVwzb.jpg",
                poster = "/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg",
                overview = "A young surgeon with Savant syndrome is recruited into the surgical unit of a prestigious hospital. The question will arise: can a person who doesn't have the ability to relate to people actually save their lives",
                voteAverage = 8.6,
                firstAirDate = "2017-09-25",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = "",
                status = ""
            ),
            TvShow(
                tvShowId = 1416,
                title = "Grey's Anatomy",
                backdrop = "/edmk8xjGBsYVIf4QtLY9WMaMcXZ.jpg",
                poster = "/clnyhPqj1SNgpAdeSS6a6fwE6Bo.jpg",
                overview = "Follows the personal and professional lives of a group of doctors at Seattle’s Grey Sloan Memorial Hospital.",
                voteAverage = 8.2,
                firstAirDate = "2005-03-27",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = "",
                status = ""
            ),
            TvShow(
                tvShowId = 88396,
                title = "The Falcon and the Winter Soldier",
                backdrop = "/b0WmHGc8LHTdGCVzxRb3IBMur57.jpg",
                poster = "/6kbAMLteGO8yyewYau6bJ683sw7.jpg",
                overview = "Following the events of “Avengers: Endgame”, the Falcon, Sam Wilson and the Winter Soldier, Bucky Barnes team up in a global adventure that tests their abilities, and their patience.",
                voteAverage = 7.9,
                firstAirDate = "2021-03-19",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = "",
                status = ""
            ),
            TvShow(
                tvShowId = 120168,
                title = "Who Killed Sara?",
                backdrop = "/dYvIUzdh6TUv4IFRq8UBkX7bNNu.jpg",
                poster = "/o7uk5ChRt3quPIv8PcvPfzyXdMw.jpg",
                overview = "Hell-bent on exacting revenge and proving he was framed for his sister's murder, Álex sets out to unearth much more than the crime's real culprit.",
                voteAverage = 7.8,
                firstAirDate = "2021-03-24",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = "",
                status = ""
            ),
            TvShow(
                tvShowId = 95057,
                title = "Superman & Lois",
                backdrop = "/pPKiIJEEcV0E1hpVcWRXyp73ZpX.jpg",
                poster = "/vlv1gn98GqMnKHLSh0dNciqGfBl.jpg",
                overview = "After years of facing megalomaniacal supervillains, monsters wreaking havoc on Metropolis, and alien invaders intent on wiping out the human race, The Man of Steel aka Clark Kent and Lois Lane come face to face with one of their greatest challenges ever: dealing with all the stress, pressures and complexities that come with being working parents in today's society.",
                voteAverage = 8.3,
                firstAirDate = "2021-02-23",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = "",
                status = ""
            ),
            TvShow(
                tvShowId = 95557,
                title = "Invincible",
                backdrop = "/6UH52Fmau8RPsMAbQbjwN3wJSCj.jpg",
                poster = "/yDWJYRAwMNKbIYT8ZB33qy84uzO.jpg",
                overview = "Mark Grayson is a normal teenager except for the fact that his father is the most powerful superhero on the planet. Shortly after his seventeenth birthday, Mark begins to develop powers of his own and enters into his father’s tutelage.",
                voteAverage = 8.9,
                firstAirDate = "2021-03-26",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = "",
                status = ""
            ),
            TvShow(
                tvShowId = 80240,
                title = "The Queen of Flow",
                backdrop = "/pnyT1foDmmXTsho2DfxN2ePI8ix.jpg",
                poster = "/fuVuDYrs8sxvEolnYr0wCSvtyTi.jpg",
                overview = "After spending seventeen years in prison unfairly, a talented songwriter seeks revenge on the men who sank her and killed her family.",
                voteAverage = 8.0,
                firstAirDate = "2018-06-12",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = "",
                status = ""
            ),
            TvShow(
                tvShowId = 105971,
                title = "The Bad Batch",
                backdrop = "/2yZ97TAUKLN8iIaLa4m48BGqBSY.jpg",
                poster = "/WjQmEWFrOf98nT5aEfUfVYz9N2.jpg",
                overview = "Follow the elite and experimental Clones of the Bad Batch as they find their way in a rapidly changing galaxy in the aftermath of the Clone Wars.",
                voteAverage = 8.7,
                firstAirDate = "2021-05-04",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = "",
                status = ""
            ),
            TvShow(
                tvShowId = 69050,
                title = "Riverdale",
                backdrop = "/qZtAf4Z1lazGQoYVXiHOrvLr5lI.jpg",
                poster = "/wRbjVBdDo5qHAEOVYoMWpM58FSA.jpg",
                overview = "Set in the present, the series offers a bold, subversive take on Archie, Betty, Veronica and their friends, exploring the surreality of small-town life, the darkness and weirdness bubbling beneath Riverdale’s wholesome facade.",
                voteAverage = 8.6,
                firstAirDate = "2017-01-26",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = "",
                status = ""
            ),
            TvShow(
                tvShowId = 1399,
                title = "Game of Thrones",
                backdrop = "/suopoADq0k8YZr4dQXcU6pToj6s.jpg",
                poster = "/u3bZgnGQ9T01sWNhyveQz0wH0Hl.jpg",
                overview = "Seven noble families fight for control of the mythical land of Westeros. Friction between the houses leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war, a neglected military order of misfits, the Night's Watch, is all that stands between the realms of men and icy horrors beyond.",
                voteAverage = 8.4,
                firstAirDate = "2011-04-17",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = "",
                status = ""
            ),
            TvShow(
                tvShowId = 62286,
                title = "Fear the Walking Dead",
                backdrop = "/58PON1OrnBiX6CqEHgeWKVwrCn6.jpg",
                poster = "/4UjiPdFKJGJYdxwRs2Rzg7EmWqr.jpg",
                overview = "What did the world look like as it was transforming into the horrifying apocalypse depicted in \"The Walking Dead\"? This spin-off set in Los Angeles, following new characters as they face the beginning of the end of the world, will answer that question.",
                voteAverage = 7.6,
                firstAirDate = "2015-08-23",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = "",
                status = ""
            ),
            TvShow(
                tvShowId = 93484,
                title = "Jupiter's Legacy",
                backdrop = "/4YKkS95v9o9c0tBVA46xIn6M1tx.jpg",
                poster = "/9yxep7oJdkj3Pla9TD9gKflRApY.jpg",
                overview = "As the world's first generation of superheroes (who received their powers in the 1930s) become the revered elder guard in the present, their superpowered children struggle to live up to the legendary feats of their parents.",
                voteAverage = 7.4,
                firstAirDate = "2021-05-07",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = "",
                status = ""
            ),
            TvShow(
                tvShowId = 69478,
                title = "The Handmaid's Tale",
                backdrop = "/jXB3OoWPkojsOP2O2OoLCeAIDRS.jpg",
                poster = "/oIkxqt6ug5zT5ZSUUyc1Iqopf02.jpg",
                overview = "Set in a dystopian future, a woman is forced to live as a concubine under a fundamentalist theocratic dictatorship. A TV adaptation of Margaret Atwood's novel.",
                voteAverage = 8.1,
                firstAirDate = "2017-04-26",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = "",
                status = ""
            ),
            TvShow(
                tvShowId = 1402,
                title = "The Walking Dead",
                backdrop = "/uro2Khv7JxlzXtLb8tCIbRhkb9E.jpg",
                poster = "/rqeYMLryjcawh2JeRpCVUDXYM5b.jpg",
                overview = "Sheriff's deputy Rick Grimes awakens from a coma to find a post-apocalyptic world dominated by flesh-eating zombies. He sets out to find his family and encounters many other survivors along the way.",
                voteAverage = 8.1,
                firstAirDate = "2010-10-31",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = "",
                status = ""
            ),
            TvShow(
                tvShowId = 18165,
                title = "The Vampire Diaries",
                backdrop = "/k7T9xRyzP41wBVNyNeLmh8Ch7gD.jpg",
                poster = "/kLEha9zVVv8acGFKTX4gjvSR2Q0.jpg",
                overview = "The story of two vampire brothers obsessed with the same girl, who bears a striking resemblance to the beautiful but ruthless vampire they knew and loved in 1864.",
                voteAverage = 8.3,
                firstAirDate = "2009-09-10",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = "",
                status = ""
            ),
            TvShow(
                tvShowId = 79460,
                title = "Legacies",
                backdrop = "/fRYwdeNjMqC30EhofPx5PlDpdun.jpg",
                poster = "/qTZIgXrBKURBK1KrsT7fe3qwtl9.jpg",
                overview = "In a place where young witches, vampires, and werewolves are nurtured to be their best selves in spite of their worst impulses, Klaus Mikaelson’s daughter, 17-year-old Hope Mikaelson, Alaric Saltzman’s twins, Lizzie and Josie Saltzman, among others, come of age into heroes and villains at The Salvatore School for the Young and Gifted.",
                voteAverage = 8.6,
                firstAirDate = "2018-10-25",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = "",
                status = ""
            ),
            TvShow(
                tvShowId = 79008,
                title = "Luis Miguel: The Series",
                backdrop = "/wkyzeBBKLhSg1Oqhky5yoiFF2hG.jpg",
                poster = "/34FaY8qpjBAVysSfrJ1l7nrAQaD.jpg",
                overview = "The series dramatizes the life story of Mexican superstar singer Luis Miguel, who has captivated audiences in Latin America and beyond for decades.",
                voteAverage = 8.0,
                firstAirDate = "2018-04-22",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = "",
                status = ""
            ),
        )

    private val tvShowResponse =
        arrayListOf(
            TvShowResponse(
                id = 63174,
                title = "Lucifer",
                backdrop = "/h48Dpb7ljv8WQvVdyFWVLz64h4G.jpg",
                poster = "/4EYPN5mVIhKLfxGruy7Dy41dTVn.jpg",
                overview = "Bored and unhappy as the Lord of Hell, Lucifer Morningstar abandoned his throne and retired to Los Angeles, where he has teamed up with LAPD detective Chloe Decker to take down criminals. But the longer he's away from the underworld, the greater the threat that the worst of humanity could escape.",
                voteAverage = 8.5,
                firstAirDate = "2016-01-25",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = emptyList(),
                status = ""
            ),
            TvShowResponse(
                id = 91557,
                title = "Ragnarok",
                backdrop = "/wu444tM9YBllq9UcBv5TeidO3j3.jpg",
                poster = "/xUtOM1QO4r8w8yeE00QvBdq58N5.jpg",
                overview = "A small Norwegian town experiencing warm winters and violent downpours seems to be headed for another Ragnarök -- unless someone intervenes in time.",
                voteAverage = 8,
                firstAirDate = "2020-01-31",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = emptyList(),
                status = ""
            ),
            TvShowResponse(
                id = 60735,
                title = "The Flash",
                backdrop = "/9Jmd1OumCjaXDkpllbSGi2EpJvl.jpg",
                poster = "/lJA2RCMfsWoskqlQhXPSLFQGXEJ.jpg",
                overview = "After a particle accelerator causes a freak storm, CSI Investigator Barry Allen is struck by lightning and falls into a coma. Months later he awakens with the power of super speed, granting him the ability to move through Central City like an unseen guardian angel. Though initially excited by his newfound powers, Barry is shocked to discover he is not the only \"meta-human\" who was created in the wake of the accelerator explosion -- and not everyone is using their new powers for good. Barry partners with S.T.A.R. Labs and dedicates his life to protect the innocent. For now, only a few close friends and associates know that Barry is literally the fastest man alive, but it won't be long before the world learns what Barry Allen has become...The Flash.",
                voteAverage = 7.7,
                firstAirDate = "2014-10-07",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = emptyList(),
                status = ""
            ),
            TvShowResponse(
                id = 71712,
                title = "The Good Doctor",
                backdrop = "/iDbIEpCM9nhoayUDTwqFL1iVwzb.jpg",
                poster = "/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg",
                overview = "A young surgeon with Savant syndrome is recruited into the surgical unit of a prestigious hospital. The question will arise: can a person who doesn't have the ability to relate to people actually save their lives",
                voteAverage = 8.6,
                firstAirDate = "2017-09-25",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = emptyList(),
                status = ""
            ),
            TvShowResponse(
                id = 1416,
                title = "Grey's Anatomy",
                backdrop = "/edmk8xjGBsYVIf4QtLY9WMaMcXZ.jpg",
                poster = "/clnyhPqj1SNgpAdeSS6a6fwE6Bo.jpg",
                overview = "Follows the personal and professional lives of a group of doctors at Seattle’s Grey Sloan Memorial Hospital.",
                voteAverage = 8.2,
                firstAirDate = "2005-03-27",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = emptyList(),
                status = ""
            ),
            TvShowResponse(
                id = 88396,
                title = "The Falcon and the Winter Soldier",
                backdrop = "/b0WmHGc8LHTdGCVzxRb3IBMur57.jpg",
                poster = "/6kbAMLteGO8yyewYau6bJ683sw7.jpg",
                overview = "Following the events of “Avengers: Endgame”, the Falcon, Sam Wilson and the Winter Soldier, Bucky Barnes team up in a global adventure that tests their abilities, and their patience.",
                voteAverage = 7.9,
                firstAirDate = "2021-03-19",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = emptyList(),
                status = ""
            ),
            TvShowResponse(
                id = 120168,
                title = "Who Killed Sara?",
                backdrop = "/dYvIUzdh6TUv4IFRq8UBkX7bNNu.jpg",
                poster = "/o7uk5ChRt3quPIv8PcvPfzyXdMw.jpg",
                overview = "Hell-bent on exacting revenge and proving he was framed for his sister's murder, Álex sets out to unearth much more than the crime's real culprit.",
                voteAverage = 7.8,
                firstAirDate = "2021-03-24",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = emptyList(),
                status = ""
            ),
            TvShowResponse(
                id = 95057,
                title = "Superman & Lois",
                backdrop = "/pPKiIJEEcV0E1hpVcWRXyp73ZpX.jpg",
                poster = "/vlv1gn98GqMnKHLSh0dNciqGfBl.jpg",
                overview = "After years of facing megalomaniacal supervillains, monsters wreaking havoc on Metropolis, and alien invaders intent on wiping out the human race, The Man of Steel aka Clark Kent and Lois Lane come face to face with one of their greatest challenges ever: dealing with all the stress, pressures and complexities that come with being working parents in today's society.",
                voteAverage = 8.3,
                firstAirDate = "2021-02-23",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = emptyList(),
                status = ""
            ),
            TvShowResponse(
                id = 95557,
                title = "Invincible",
                backdrop = "/6UH52Fmau8RPsMAbQbjwN3wJSCj.jpg",
                poster = "/yDWJYRAwMNKbIYT8ZB33qy84uzO.jpg",
                overview = "Mark Grayson is a normal teenager except for the fact that his father is the most powerful superhero on the planet. Shortly after his seventeenth birthday, Mark begins to develop powers of his own and enters into his father’s tutelage.",
                voteAverage = 8.9,
                firstAirDate = "2021-03-26",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = emptyList(),
                status = ""
            ),
            TvShowResponse(
                id = 80240,
                title = "The Queen of Flow",
                backdrop = "/pnyT1foDmmXTsho2DfxN2ePI8ix.jpg",
                poster = "/fuVuDYrs8sxvEolnYr0wCSvtyTi.jpg",
                overview = "After spending seventeen years in prison unfairly, a talented songwriter seeks revenge on the men who sank her and killed her family.",
                voteAverage = 8,
                firstAirDate = "2018-06-12",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = emptyList(),
                status = ""
            ),
            TvShowResponse(
                id = 105971,
                title = "The Bad Batch",
                backdrop = "/2yZ97TAUKLN8iIaLa4m48BGqBSY.jpg",
                poster = "/WjQmEWFrOf98nT5aEfUfVYz9N2.jpg",
                overview = "Follow the elite and experimental Clones of the Bad Batch as they find their way in a rapidly changing galaxy in the aftermath of the Clone Wars.",
                voteAverage = 8.7,
                firstAirDate = "2021-05-04",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = emptyList(),
                status = ""
            ),
            TvShowResponse(
                id = 69050,
                title = "Riverdale",
                backdrop = "/qZtAf4Z1lazGQoYVXiHOrvLr5lI.jpg",
                poster = "/wRbjVBdDo5qHAEOVYoMWpM58FSA.jpg",
                overview = "Set in the present, the series offers a bold, subversive take on Archie, Betty, Veronica and their friends, exploring the surreality of small-town life, the darkness and weirdness bubbling beneath Riverdale’s wholesome facade.",
                voteAverage = 8.6,
                firstAirDate = "2017-01-26",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = emptyList(),
                status = ""
            ),
            TvShowResponse(
                id = 1399,
                title = "Game of Thrones",
                backdrop = "/suopoADq0k8YZr4dQXcU6pToj6s.jpg",
                poster = "/u3bZgnGQ9T01sWNhyveQz0wH0Hl.jpg",
                overview = "Seven noble families fight for control of the mythical land of Westeros. Friction between the houses leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war, a neglected military order of misfits, the Night's Watch, is all that stands between the realms of men and icy horrors beyond.",
                voteAverage = 8.4,
                firstAirDate = "2011-04-17",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = emptyList(),
                status = ""
            ),
            TvShowResponse(
                id = 62286,
                title = "Fear the Walking Dead",
                backdrop = "/58PON1OrnBiX6CqEHgeWKVwrCn6.jpg",
                poster = "/4UjiPdFKJGJYdxwRs2Rzg7EmWqr.jpg",
                overview = "What did the world look like as it was transforming into the horrifying apocalypse depicted in \"The Walking Dead\"? This spin-off set in Los Angeles, following new characters as they face the beginning of the end of the world, will answer that question.",
                voteAverage = 7.6,
                firstAirDate = "2015-08-23",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = emptyList(),
                status = ""
            ),
            TvShowResponse(
                id = 93484,
                title = "Jupiter's Legacy",
                backdrop = "/4YKkS95v9o9c0tBVA46xIn6M1tx.jpg",
                poster = "/9yxep7oJdkj3Pla9TD9gKflRApY.jpg",
                overview = "As the world's first generation of superheroes (who received their powers in the 1930s) become the revered elder guard in the present, their superpowered children struggle to live up to the legendary feats of their parents.",
                voteAverage = 7.4,
                firstAirDate = "2021-05-07",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = emptyList(),
                status = ""
            ),
            TvShowResponse(
                id = 69478,
                title = "The Handmaid's Tale",
                backdrop = "/jXB3OoWPkojsOP2O2OoLCeAIDRS.jpg",
                poster = "/oIkxqt6ug5zT5ZSUUyc1Iqopf02.jpg",
                overview = "Set in a dystopian future, a woman is forced to live as a concubine under a fundamentalist theocratic dictatorship. A TV adaptation of Margaret Atwood's novel.",
                voteAverage = 8.1,
                firstAirDate = "2017-04-26",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = emptyList(),
                status = ""
            ),
            TvShowResponse(
                id = 1402,
                title = "The Walking Dead",
                backdrop = "/uro2Khv7JxlzXtLb8tCIbRhkb9E.jpg",
                poster = "/rqeYMLryjcawh2JeRpCVUDXYM5b.jpg",
                overview = "Sheriff's deputy Rick Grimes awakens from a coma to find a post-apocalyptic world dominated by flesh-eating zombies. He sets out to find his family and encounters many other survivors along the way.",
                voteAverage = 8.1,
                firstAirDate = "2010-10-31",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = emptyList(),
                status = ""
            ),
            TvShowResponse(
                id = 18165,
                title = "The Vampire Diaries",
                backdrop = "/k7T9xRyzP41wBVNyNeLmh8Ch7gD.jpg",
                poster = "/kLEha9zVVv8acGFKTX4gjvSR2Q0.jpg",
                overview = "The story of two vampire brothers obsessed with the same girl, who bears a striking resemblance to the beautiful but ruthless vampire they knew and loved in 1864.",
                voteAverage = 8.3,
                firstAirDate = "2009-09-10",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = emptyList(),
                status = ""
            ),
            TvShowResponse(
                id = 79460,
                title = "Legacies",
                backdrop = "/fRYwdeNjMqC30EhofPx5PlDpdun.jpg",
                poster = "/qTZIgXrBKURBK1KrsT7fe3qwtl9.jpg",
                overview = "In a place where young witches, vampires, and werewolves are nurtured to be their best selves in spite of their worst impulses, Klaus Mikaelson’s daughter, 17-year-old Hope Mikaelson, Alaric Saltzman’s twins, Lizzie and Josie Saltzman, among others, come of age into heroes and villains at The Salvatore School for the Young and Gifted.",
                voteAverage = 8.6,
                firstAirDate = "2018-10-25",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = emptyList(),
                status = ""
            ),
            TvShowResponse(
                id = 79008,
                title = "Luis Miguel: The Series",
                backdrop = "/wkyzeBBKLhSg1Oqhky5yoiFF2hG.jpg",
                poster = "/34FaY8qpjBAVysSfrJ1l7nrAQaD.jpg",
                overview = "The series dramatizes the life story of Mexican superstar singer Luis Miguel, who has captivated audiences in Latin America and beyond for decades.",
                voteAverage = 8,
                firstAirDate = "2018-04-22",
                lastAirDate = "",
                numberOfSeasons = 0,
                genres = emptyList(),
                status = ""
            ),
        )


    fun generateMovies(): List<Movie> = movies

    fun generateListResponseMovie() = ListResponse(moviesResponse, 1, 1)

    fun getMoviePagingData() = flow { emit(PagingData.from(movies)) }

    fun generateTvShows() = tvShow

    fun generateListResponseTvShow() = ListResponse(tvShowResponse, 1, 1)

    fun getMovieResponse(id: Int) =
        moviesResponse.firstOrNull { movie -> movie.id == id }!!

    fun getMovieEntity(id: Int) =
        movies.firstOrNull { movie -> movie.id == id }!!
            .mapToEntity()

    fun getTvShowResponse(id: Int) =
        tvShowResponse.firstOrNull { tvShow -> tvShow.id == id }!!

    fun getTvShowEntity(id: Int) =
        tvShow.firstOrNull { tvShow -> tvShow.id == id }!!
            .mapToEntity()
}