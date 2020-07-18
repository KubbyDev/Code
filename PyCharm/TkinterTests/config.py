import configparser


colors = None
partitions = None


def __to_int(dic, values):
    for val in values:
        dic[val] = int(dic[val])


def __parse_colors(parser):
    global colors
    colors = dict(parser)


def __parse_partitions(parser):
    global partitions
    partitions = dict(parser)
    __to_int(partitions, ['width', 'note_height'])


def __read_config():
    parser = configparser.ConfigParser()
    parser.read('configuration.conf')
    __parse_colors(parser['COLORS'])
    __parse_partitions(parser['PARTITIONS'])
__read_config()