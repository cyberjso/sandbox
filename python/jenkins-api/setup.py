from setuptools import setup

setup(
    name = "job-tracker",
    version = "0.0.1-SNAPSHOT",
    packages=['job-tracker'],
    install_requires=[
        'jenkinsapi',
    ]

)