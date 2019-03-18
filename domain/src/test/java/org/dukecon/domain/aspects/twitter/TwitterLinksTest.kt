package org.dukecon.domain.aspects.twitter

import org.junit.Test

class TwitterLinksTest {
    var twitterLinks: TwitterLinks = TwitterLinks()

    @Test
    internal fun mapsEmptyTwitterAsEmptyString() {
        val link = twitterLinks.getNormalizedTwitterUrl("")
        assert(link.equals(""))
    }

    @Test
    internal fun mapsTwitterHandleToTwitterUrl() {
        val link = twitterLinks.getNormalizedTwitterUrl("@joespeaker")
        assert(link.equals("https://twitter.com/joespeaker"))
    }

    @Test
    internal fun keepsFullTwitteUrl() {
        val link = twitterLinks.getNormalizedTwitterUrl("https://twitter.com/joespeaker")
        assert(link.equals("https://twitter.com/joespeaker"))
    }

    @Test
    fun getsHandleFromFullLink() {
        val handle = twitterLinks.getHandle("https://twitter.com/joespeaker")
        assert(handle.equals("@joespeaker"))
    }

    @Test
    fun getsEmptyHandleFromBrokenLink() {
        val handle = twitterLinks.getHandle("http://http://")
        assert(handle.equals(""))
    }

    @Test
    fun getsHandleFromHandle() {
        val handle = twitterLinks.getHandle("@joespeaker")
        assert(handle.equals("@joespeaker"))
    }

    @Test
    fun getsHandleFromSimpleName() {
        val handle = twitterLinks.getHandle("joespeaker")
        assert(handle.equals("@joespeaker"))
    }
}