cmake_minimum_required(VERSION 3.22.1)
project("clicker")

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

add_library(
    clicker
    SHARED
    clicker.cpp
)

find_library(log-lib log)

target_link_libraries(
    clicker
    ${log-lib}
)
