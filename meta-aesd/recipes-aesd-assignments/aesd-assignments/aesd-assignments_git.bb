# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# TODO: Set this  with the path to your assignments rep.  Use ssh protocol and see lecture notes
# about how to setup ssh-agent for passwordless access
SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-danganhphuc1131997;protocol=ssh;branch=master"

PV = "1.0+git${SRCPV}"
# TODO: set to reference a specific commit hash in your assignment repo
SRCREV = "72ea27e60d10e42a4a6cc4eebee04d2f230b6ebb"

# This sets your staging directory based on WORKDIR, where WORKDIR is defined at
# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-WORKDIR
# We reference the "server" directory here to build from the "server" directory
# in your assignments repo
S = "${WORKDIR}/git/server"

# See https://git.yoctoproject.org/poky/plain/meta/conf/bitbake.conf?h=kirkstone
FILES:${PN} += "${bindir}/aesdsocket"
TARGET_LDFLAGS += "-pthread -lrt"

inherit update-rc.d

# Reference class
INITSCRIPT_PACKAGES="${PN}"

# Flag package as one which uses init scripts
INITSCRIPT_NAME:${PN} = "aesdsocket-start-stop"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install () {
	install -d ${D}${bindir}
	install -m 0755 ${S}/aesdsocket ${D}${bindir}/
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${S}/aesdsocket-start-stop ${D}${sysconfdir}/init.d
}