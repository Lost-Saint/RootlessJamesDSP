@file:Suppress("NOTHING_TO_INLINE")
package app.siphondsp.utils

import app.siphondsp.BuildConfig

inline fun isRootless() = BuildConfig.ROOTLESS && !BuildConfig.PLUGIN
inline fun isRoot() = !BuildConfig.ROOTLESS && !BuildConfig.PLUGIN
inline fun isPlugin() = BuildConfig.PLUGIN
