#!/usr/bin/env bash
# ui.sh — drive the app on an Android device or AVD for a device proof (PLAN ✅ checks that read
# "on device", the docs/runbooks/login-failure-checklist.md rows, week gates). Born in the D9–D11
# session scratchpads, committed at the Week-2 gate (D12).
#
# The loop: dump the accessibility tree with uiautomator, find a node by its label, tap its
# centre, type into the focused field, screenshot. Flutter widgets show up with their semantics
# label as content-desc and text fields as EditText, so every tap is by label, never by pixel.
#
#   scripts/ui.sh tree                 # "desc|text|class|bounds|enabled=|focused=" per labelled node
#   scripts/ui.sh tap 'Send code'      # tap the first node whose desc or text contains the label
#   scripts/ui.sh tap 'Log out' 2      # … the second match
#   scripts/ui.sh field                # focus the first EditText, cursor at the end (field 2 = second)
#   scripts/ui.sh type you@example.com # type into the focused field (adb input text: no spaces)
#   scripts/ui.sh shot d12-01-login    # screenshot → $UI_SHOTS/d12-01-login.png
#   scripts/ui.sh launch | kill | clear   # start the app / force-stop it / wipe it (signed-out device)
#
# Environment: ADB (default: adb on PATH, else the Homebrew platform-tools path), UI_SHOTS
# (screenshot directory, default the current directory), UI_PKG (default com.margai.app),
# ANDROID_SERIAL (adb's own device selector when more than one is attached).
#
# Two things learnt the hard way: right after `launch` the first dump can still show the previous
# window, so dump again before believing a stale-looking screen; and after a round trip through
# another screen refocus the field (`field`) before `type`, or the text goes nowhere.
set -euo pipefail

ADB="${ADB:-$(command -v adb || echo /opt/homebrew/share/android-commandlinetools/platform-tools/adb)}"
UI_SHOTS="${UI_SHOTS:-.}"
PKG="${UI_PKG:-com.margai.app}"

usage() {
  sed -n '2,/^set -euo/p' "$0" | sed '$d' | sed 's/^# \{0,1\}//'
}

# Raw uiautomator XML of the current window.
dump() {
  "$ADB" shell uiautomator dump /sdcard/ui.xml >/dev/null 2>&1
  "$ADB" shell cat /sdcard/ui.xml
}

# One line per node that carries a label, a text, or is a field or a button:
# content-desc|text|class|bounds|enabled=…|focused=…
tree() {
  local xml
  xml="$(mktemp -t ui-tree)"
  dump >"$xml"
  python3 - "$xml" <<'PY'
import sys
import xml.etree.ElementTree as ET

root = ET.parse(sys.argv[1]).getroot()
for n in root.iter("node"):
    desc, text, cls = n.get("content-desc", ""), n.get("text", ""), n.get("class", "")
    if desc or text or cls.endswith("EditText") or cls.endswith("Button"):
        print("|".join([desc, text, cls.split(".")[-1], n.get("bounds", ""),
                        "enabled=" + n.get("enabled", ""), "focused=" + n.get("focused", "")]))
PY
  rm -f "$xml"
}

# bounds "[x1,y1][x2,y2]" → "x y" of the centre
center() {
  python3 -c 'import re, sys; x1, y1, x2, y2 = map(int, re.findall(r"\d+", sys.argv[1])); print((x1 + x2) // 2, (y1 + y2) // 2)' "$1"
}

# tap <label-substring> [nth]
tap() {
  local label="${1:?tap needs a label}" nth="${2:-1}" line bounds x y
  line="$(tree | grep -F -- "$label" | sed -n "${nth}p" || true)"
  if [ -z "$line" ]; then
    echo "tap: no node matches '$label' (match $nth); the tree is:" >&2
    tree >&2
    return 1
  fi
  bounds="$(printf '%s' "$line" | cut -d'|' -f4)"
  read -r x y < <(center "$bounds")
  echo "tap '$label' -> ($x,$y)"
  "$ADB" shell input tap "$x" "$y"
}

# field [nth]: focus the nth EditText and move the cursor to its end
field() {
  local nth="${1:-1}" line bounds x y
  line="$(tree | awk -F'|' '$3 == "EditText"' | sed -n "${nth}p")"
  if [ -z "$line" ]; then
    echo "field: no EditText number $nth on screen" >&2
    return 1
  fi
  bounds="$(printf '%s' "$line" | cut -d'|' -f4)"
  read -r x y < <(center "$bounds")
  "$ADB" shell input tap "$x" "$y"
  "$ADB" shell input keyevent KEYCODE_MOVE_END
}

# type <text>: into the focused field; adb's `input text` takes no spaces
type_text() { "$ADB" shell input text "${1:?type needs text}"; }

# shot <name>: PNG screenshot to $UI_SHOTS/<name>.png
shot() {
  local name="${1:?shot needs a name}"
  mkdir -p "$UI_SHOTS"
  "$ADB" exec-out screencap -p >"$UI_SHOTS/$name.png"
  echo "shot $UI_SHOTS/$name.png"
}

launch()   { "$ADB" shell am start -n "$PKG/.MainActivity" >/dev/null; }
kill_app() { "$ADB" shell am force-stop "$PKG"; }
clear_app() { "$ADB" shell pm clear "$PKG"; }

case "${1:-}" in
  tree)   tree ;;
  dump)   dump ;;
  tap)    shift; tap "$@" ;;
  field)  shift; field "$@" ;;
  type)   shift; type_text "$@" ;;
  shot)   shift; shot "$@" ;;
  launch) launch ;;
  kill)   kill_app ;;
  clear)  clear_app ;;
  ""|-h|--help|help) usage ;;
  *) echo "ui.sh: unknown command '$1'" >&2; usage >&2; exit 2 ;;
esac
