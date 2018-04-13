package org.dukecon.domain.aspects.twitter

import org.junit.Test

class TwitterLinksTest {
    var mapper: TwitterLinkMapper = TwitterLinkMapper()

    @Test
    internal fun mapsEmptyTwitterAsEmptyString() {
        val link = mapper.getNormalizedTwitterUrl("")
        assert(link.equals(""))
    }

    @Test
    internal fun mapsTwitterHandleToTwitterUrl() {
        val link = mapper.getNormalizedTwitterUrl("@joespeaker")
        assert(link.equals("https://twitter.com/joespeaker"))
    }

    @Test
    internal fun keepsFullTwitteUrl() {
        val link = mapper.getNormalizedTwitterUrl("https://twitter.com/joespeaker")
        assert(link.equals("https://twitter.com/joespeaker"))
    }

    @Test
    fun getsHandleFromFullLink() {
        val handle = mapper.getHandle("https://twitter.com/joespeaker")
        assert(handle.equals("@joespeaker"))
    }

    @Test
    fun getsHandleFromHandle() {
        val handle = mapper.getHandle("@joespeaker")
        assert(handle.equals("@joespeaker"))
    }

    @Test
    fun getsHandleFromSimpleName() {
        val handle = mapper.getHandle("joespeaker")
        assert(handle.equals("@joespeaker"))
    }
}